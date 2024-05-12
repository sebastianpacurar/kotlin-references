package coroutines

import kotlin.concurrent.thread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory


private val logger: Logger = LoggerFactory.getLogger("coroutines.basics")


fun main() {
    println(" Thread example execution reached")
    threadExample()

    println("\n Coroutine example execution reached")
    coroutineExample()

    println("\n Delay example execution reached")
    delayExample()

    println("\n RunBlocking example execution reached")
    runBlockingExample()
}


// the application ends when all threads have finished
// the main thread, and Thread-0 (thread started in the thread{}) run in parallel, concurrently
fun threadExample() = runBlocking {
    // called from the scope of the main function
    logger.info("App starts in function: threadExample() on thread: ${Thread.currentThread().name}")

    thread { // creates a background thread
        //  does not block the code of the main thread, running in parallel with the main thread
        logger.info("Start Process in function: threadExample() on thread: ${Thread.currentThread().name}")
        Thread.sleep(1000)
        logger.info("Finish process in function: threadExample() on thread: ${Thread.currentThread().name}")
    }

    logger.info("End threadExample() on thread: ${Thread.currentThread().name}")
}


// the application does not wait for a coroutine to finish
fun coroutineExample() = runBlocking {
    logger.info("Start coroutineExample() on thread: ${Thread.currentThread().name}")

    GlobalScope.launch { // creates a background coroutine that runs (operates) within a thread
        logger.info("Start process in function: coroutineExample() on thread: ${Thread.currentThread().name}")
        Thread.sleep(1000)
        logger.info("Finish process in function: coroutineExample() on thread: ${Thread.currentThread().name}")
    }

    Thread.sleep(2000)  // block the main thread, and wait for the coroutine to finish (an impractical way to wait)
    logger.info("End coroutineExample() on thread: ${Thread.currentThread().name}")
}


/*
    delay() is a suspend function, meaning that it suspends the coroutine for a specified amount of time without blocking the main thread
      the suspending functions are only allowed to be called from a coroutine or from another suspending function
      they cannot be called from outside a coroutine
    runBlocking() is a coroutine builder which creates a coroutine that blocks the main thread until the coroutine completes its execution
    GlobalScope.launch() is non-blocking
* */
fun delayExample() = runBlocking {

    logger.info("Start delayExample() on thread: ${Thread.currentThread().name}")

    GlobalScope.launch {
        logger.info("Start Process in function: delayExample() on thread: ${Thread.currentThread().name}")
        delay(1000L) // coroutine is suspended but Thread is not blocked
        logger.info("Finish Process in function: delayExample() on thread: ${Thread.currentThread().name}")
    }

    // using delay outside the runBlocking {} will not work
    runBlocking { // creates a coroutine tha blocks the current main thread
        delay(2000L)
    }

    logger.info("End delayExample() on thread: ${Thread.currentThread().name}")
}


/*
    Sequential steps:
    (1) function called from main() function which starts to execute in the main thread
    (2) variable functionName is assigned the name of the current function in the main thread
    (3) runBlocking() launches a new coroutine that blocks the main thread, meaning that this coroutine runs on the main thread
    (4) the first logger.info() statement runs on the main thread, because it lies within the scope of runBlocking method, which is running on the main thread
    (5) GlobalScope.launch() creates a child coroutine in a background thread, meaning that its content starts executing concurrently in the background
    (6) delay(2000L) is present within the scope of runBlocking, meaning it executes in the main thread
    (7) the last logger.info() statement runs on the main thread
* */
fun runBlockingExample() { // (1)
    val functionName = ::runBlockingExample.name // (2)

    runBlocking { // (3)

        logger.info("Start $functionName on thread: ${Thread.currentThread().name}") // (4)

        GlobalScope.launch { // executes on T1 // (5)
            logger.info("Start Process in function: $functionName on thread: ${Thread.currentThread().name}") // T1
            delay(1000L) // coroutine is suspended but T1 is free (not blocked)
            logger.info("Finish process in function: $functionName on thread: ${Thread.currentThread().name}") // either T1 or some other thread
        }

        delay(2000L) // (6)

        logger.info("End $functionName on thread: ${Thread.currentThread().name}") // (7)
    }
}

