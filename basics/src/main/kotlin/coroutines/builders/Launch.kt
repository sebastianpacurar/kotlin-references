package coroutines.builders

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/*

  - global coroutines are top-level coroutines and can survive the entire life of the application
  - local coroutines can survive for the duration of a specific component
        - example logging in on LoginScreen, starts the login process. When the user reaches HomeScreen, the LoginScreen gets destroyed, along with the coroutine

  - launch{} coroutine builder is of type 'Fire and Forget'
        - it starts a coroutine that runs independently in the background without returning a result immediately to the caller
        - Fire and Forget is useful for tasks where you don't need to immediately deal with the result or where the result doesn't need to be returned to the caller
        - commonly used for tasks such as background processing, concurrent operations, or tasks that need to be executed without blocking the calling thread
        - example scenarios: logging events, sending analytics data, performing background calculations, sending push notifications, performing database operations, updating UI elements
  - local launch{} will inherit the coroutine scope of the immediate parent coroutine
  - launch{} is an extended function of the CoroutineScope interface, and returns a Job object. Job object can be used for canceling a coroutine or waiting for it to finish

*/


private val logger: Logger = LoggerFactory.getLogger("coroutines.builders.launch")


fun main() {
    println(" GlobalScope.launch{} execution reached")
    globalLaunch()

    println("\n Local launch{} execution reached")
    localLaunch()

    println("\n JobObject execution reached")
    jobObject()
}


fun globalLaunch() {
    val functionName = ::globalLaunch.name
    runBlocking {
        logger.info("Start main thread in: $functionName on thread: ${Thread.currentThread().name}")

        GlobalScope.launch { // this runs on Thread T1
            logger.info("Start Global Launch in: $functionName on thread: ${Thread.currentThread().name}")
            delay(1000)
            logger.info("End Global Launch in: $functionName on thread: ${Thread.currentThread().name}")
        }

        delay(2000)
        logger.info("End main thread in: $functionName on thread: ${Thread.currentThread().name}")
    }
}


// contents of launch will run on the main thread, because launch{} is in the scope of the runBlocking{} coroutine builder
fun localLaunch() {
    val functionName = ::localLaunch.name
    runBlocking {
        logger.info("Start main thread in: $functionName on thread: ${Thread.currentThread().name}")

        launch { // this runs on the main thread
            logger.info("Start Local Launch in: $functionName on thread: ${Thread.currentThread().name}")
            delay(1000) // this suspends the coroutine, meaning that the next statement will execute on the main thread or another thread
            logger.info("End Local Launch in: $functionName on thread: ${Thread.currentThread().name}")
        }

        delay(2000)
        logger.info("End main thread in: $functionName on thread: ${Thread.currentThread().name}")
    }
}


// job object can cancel the object, or wait for the coroutine to finish
fun jobObject() {
    val functionName = ::jobObject.name
    runBlocking {
        logger.info("Start main thread in: $functionName on thread: ${Thread.currentThread().name}")

        val job: Job = launch {
            logger.info("Start Local Launch in: $functionName on thread: ${Thread.currentThread().name}")
            delay(1000)
            logger.info("End Local Launch in: $functionName on thread: ${Thread.currentThread().name}")
        }

        job.join()  // wait for the coroutine to finish, without needing an explicit delay()
        logger.info("End main thread in: $functionName on thread: ${Thread.currentThread().name}")
    }
}

