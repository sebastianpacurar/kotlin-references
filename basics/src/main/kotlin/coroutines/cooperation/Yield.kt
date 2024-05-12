package coroutines.cooperation

import Fp
import kotlinx.coroutines.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.coroutineContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis


/*
    - suspend the coroutine temporarily, allowing other coroutines to execute
    - beneficial in scenarios where there are multiple coroutines running and fair execution among them is needed
    - once other coroutines have had a chance to execute, the suspended coroutine will resume its execution from where it left off, continuing with the next iteration of the loop.
 */



fun main() {
    val dir = File(Fp.yieldFp)

    if (!dir.exists()) {
        dir.mkdirs()
    }

    fileOperations()
}


suspend fun createAndWriteToFile(fileIndex: Int, timePattern: DateTimeFormatter) {
    val outputFile = File("${Fp.yieldFp}yield_concurrent_$fileIndex.txt")
    val coroutineId = coroutineContext[CoroutineName]?.name ?: coroutineContext.hashCode()

    repeat(50) { iteration ->
        println("File index: $fileIndex - Iteration $iteration performed by $coroutineId in yield_concurrent_$fileIndex.txt file")
        val randomInt = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val currTime = LocalDateTime.now().format(timePattern)

        outputFile.appendText("Iteration $iteration | Written by: $coroutineId | At: $currTime | Random Int: $randomInt\n")

        // suspend the coroutine temporarily, allowing other coroutines to execute
        yield()
    }
}

suspend fun removeFile(fileIndex: Int, timePattern: DateTimeFormatter) {
    val fp = "${Fp.yieldFp}yield_concurrent_${fileIndex}.txt"
    val file = File(fp)
    val coroutineId = coroutineContext[CoroutineName]?.name ?: coroutineContext.hashCode()

    if (file.exists()) {
        val currTime = LocalDateTime.now().format(timePattern)
        file.delete()
        println("Deleted yield_concurrent_${fileIndex} | By: $coroutineId | At: $currTime")
    }

    // suspend the coroutine temporarily, allowing other coroutines to execute
    yield()
}


fun fileOperations() {
    val timePattern = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")

    val deleteJobs = mutableListOf<Job>()
    val createJobs = mutableListOf<Job>()

    runBlocking {
        // measure time for both launching the coroutines and deleting process
        val filesRemoveTime = measureTimeMillis {
            // launch 20 coroutines with custom CoroutineName to handle removeFile
            for (i in 1..20) {
                deleteJobs.add(launch(CoroutineName("deletion_coroutine_$i")) { removeFile(i, timePattern) })
            }
            deleteJobs.joinAll()
        }

        // measure time for both launching the coroutines and creating files + appending data
        val filesCreationTime = measureTimeMillis {
            for (i in 1..20) {
                // launch 20 coroutines with custom CoroutineName to handle createAndWriteToFile
                createJobs.add(launch(CoroutineName("creation_coroutine_$i")) { createAndWriteToFile(i, timePattern) })
            }
            createJobs.joinAll()
        }

        println("Files deletion time: $filesRemoveTime ms")
        println("Files creation time: $filesCreationTime ms")
    }
}

