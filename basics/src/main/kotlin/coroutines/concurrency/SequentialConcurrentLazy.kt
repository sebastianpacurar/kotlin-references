package coroutines.concurrency

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis


private val logger: Logger = LoggerFactory.getLogger("coroutines.concurrency")


fun main() {
    println(" SequentialExec execution reached")
    sequentialExec()

    println(" ConcurrentExec execution reached")
    concurrentExec()

    println(" LazyExec execution reached")
    lazyExec()
}


suspend fun getMsg1(): String {
    delay(1000L)
    logger.info("Msg1 Print Line")
    return "Hello "
}

suspend fun getMsg2(): String {
    delay(2000L)
    logger.info("Msg2 Print Line")
    return "World!"
}


fun sequentialExec() = runBlocking {
    val time = measureTimeMillis {
        val msgOne = getMsg1() // invoke 1st
        val msgTwo = getMsg2() // invoke 2nd
        logger.info("String message is $msgOne $msgTwo") // combine results
    }
    logger.info("Time it took: $time ms")
}


fun concurrentExec() = runBlocking {
    val time = measureTimeMillis {
        val msgOne: Deferred<String> = async { getMsg1() } // concurrent coroutine
        val msgTwo: Deferred<String> = async { getMsg2() } // concurrent coroutine
        logger.info("String message is ${msgOne.await()} ${msgTwo.await()}") // call await() on both Deferred objects
    }
    logger.info("Time it took: $time ms")

}


fun lazyExec() = runBlocking {
    val msgOne: Deferred<String> = async(start = CoroutineStart.LAZY) { getMsg1() } // lazy concurrent coroutine
    val msgTwo: Deferred<String> = async(start = CoroutineStart.LAZY) { getMsg2() } // lazy concurrent coroutine

    // since await() isn't called, then the above messages suspend functions are not executed

    logger.info("Program end")
}


