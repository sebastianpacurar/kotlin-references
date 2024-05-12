package coroutines.builders

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/*

  - async{} coroutine builder is used for starting a coroutine that computes a result asynchronously
  - unlike launch{}, async{} returns a Deferred<T> object representing a future result of the computation
  - async{} is commonly used when you need to perform a computation that produces a result, and you want to continue with other work while waiting for the result
  - examples of scenarios where async{} is useful include fetching data from a remote server, performing heavy computations, or executing I/O-bound tasks
  - when using async{}, you typically await the result using await() function to retrieve the computed value
  - async{} is often used in conjunction with await() or awaitAll() for handling multiple asynchronous computations concurrently

*/

private val logger: Logger = LoggerFactory.getLogger("coroutines.builders.async")


fun main() {
    println(" Async execution reached")
    asyncFunction()

    println("\n Await All execution reached")
    awaitAllExample()
}


fun asyncFunction() = runBlocking {
    logger.info("Start asyncFunction() on thread: ${Thread.currentThread().name}")

    val jobDeferredNoReturn: Deferred<Unit> = async {
        logger.info("Start Deferred<Unit> process in: asyncFunction() on thread: ${Thread.currentThread().name}")
        delay(1000L)
        logger.info("End Deferred<Unit> process in: asyncFunction() on thread: ${Thread.currentThread().name}")
    }

    val jobDeferredStringReturn: Deferred<String> = async {
        logger.info("Start Deferred<String> process in: asyncFunction() on thread: ${Thread.currentThread().name}")
        delay(1000L)
        logger.info("End Deferred<String> in: asyncFunction() on thread: ${Thread.currentThread().name}")
        "'My async function string'"
    }

    jobDeferredNoReturn.join()

    // using await() allows to use the returned value from an async{}
    val result: String = jobDeferredStringReturn.await()
    logger.info("Result from await is: $result")
    logger.info("End asyncFunction() on thread: ${Thread.currentThread().name}")
}


fun awaitAllExample() = runBlocking {
    logger.info("Start awaitAllExample() on thread: ${Thread.currentThread().name}")

    // create a list to hold Deferred<String> objects
    val deferredResults = mutableListOf<Deferred<String>>()

    // launch five coroutines asynchronously
    for (i in 1..5) {
        val deferredResult: Deferred<String> = async {
            logger.info("Start coroutine $i in: awaitAllExample() on thread: ${Thread.currentThread().name}")
            delay(1000L)
            logger.info("End coroutine $i in: awaitAllExample() on thread: ${Thread.currentThread().name}")
            "Result from coroutine $i" // return a result
        }
        deferredResults.add(deferredResult) // add the Deferred object to the list
    }

    // Wait for all coroutines to complete and collect their results
    val allResults = measureTimeMillis {

        // using awaitAll() to wait for all coroutines to finish
        val results = deferredResults.awaitAll()

        results.forEachIndexed { index, result ->
            // Print each result along with its index
            logger.info("Result $index: $result")
        }
    }
    logger.info("All coroutines completed in $allResults ms")
}


