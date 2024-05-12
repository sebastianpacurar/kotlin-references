package coroutines.cooperation

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/*
  Cooperation:
  - a coroutine can be canceled in case it takes too long to finish
  - in order to cancel a coroutine, it needs to be cooperative
  - to cancel a coroutine, job.cancel() needs to be called on the job object
        - if the coroutine is cooperative it will get canceled, if not then it will continue normally
        - if the coroutine fails to cancel, then job.join() will wait for the coroutine function
        - job.cancel() and job.join() are used in conjunction, leading to a function job.cancelAndJoin()
  - job.cancelAndJoin() = if the coroutine is cooperative then cancel it, else wait for the coroutine to finish

  - 2 ways to make the coroutine cooperative:
        1) periodically invoke a suspending function that checks for cancellation
            - only those suspending functions that belong to kotlinx.coroutines package will make coroutine cooperative
            - delay(), yield(), withContext(), withTimeout() are kotlinx.coroutines related coroutines
        2) explicitly check for the cancellation status within the coroutine using CoroutineScope.isActive flag
            - this is applicable only for coroutines, since Threads cannot cancel themselves internally

  Exceptions:
  - cancellable suspending functions such as yield(), delay(), withContext(), withTimeout(), throw CancellationException on the coroutine cancellation
  - a suspending function cannot be executed from the finally{}, because the coroutine has been already cancelled
  - to do this withContext(NonCancellable) function can be used
  - print own cancellation message using job.cancel(CancellationException("My Message"))

 */


private val logger: Logger = LoggerFactory.getLogger("coroutines.cooperation")


fun main() {
    println(" delayExample execution started")
    delayExample()

    println("\n isActiveFlagExample execution started")
    isActiveFlagExample()

    println("\n ExceptionExample execution reached")
    exceptionExample()

    println("\n WithContextExample execution reached")
    withContextExample()

    println("\n CustomCancellationMessage execution reached")
    customCancellationMessage()
}


/*
*  Cooperation
* */

// make coroutine cooperative through delay() function
fun delayExample() {
    var result = 0

    runBlocking {
        val job: Job = launch {

            for (i in 1..100) {
                print("$i-$result ")
                result += i
                delay(50L) // make the coroutine cancellable
            }
        }

        delay(200L)  // wait for a bit until cancel
        job.cancelAndJoin()  // cancel and join used together
    }
}


// make coroutine cooperative through isActive flag
fun isActiveFlagExample() {
    var result = 0

    runBlocking {
        val job: Job = launch(Dispatchers.Default) { // need to specify the type of Dispatchers when using isActive flag

            for (i in 1..100) {
                print("$i-$result ")
                result += i

                if (!isActive) {
                    return@launch // return at the coroutine level
                }

                //using Thread.sleep() for this example, to enable cooperation through CoroutineScope.isActive flag
                Thread.sleep(50)
            }
        }

        delay(200L)  // wait for a bit until cancel
        job.cancelAndJoin()  // cancel and join used together
    }
}


/*
*  Exceptions
* */

fun exceptionExample() {
    var result = 0

    runBlocking {
        val job: Job = launch(Dispatchers.Default) {
            try {
                for (i in 1..100) {
                    print("$i-$result ")
                    result += i
                    delay(10)
                }
            } catch (e: CancellationException) {
                println()
                logger.info("Exception caught in exceptionExample(): ${e.javaClass.simpleName}")
            } finally {
                logger.info("Closing resource of exceptionExample()")
            }
        }

        delay(200)
        job.cancelAndJoin()
    }
    logger.info("End of exceptionExample() reached")
}


fun withContextExample() {
    var res = 0

    runBlocking {
        val job: Job = launch(Dispatchers.Default) {
            try {
                for (i in 1..100) {
                    print("$i-$res ")
                    res += i
                    delay(10)
                }
            } catch (e: CancellationException) {
                println()
                logger.info("Exception caught in withContextExample(): ${e.javaClass.simpleName}")
            } finally {

                // this is a coroutine builder, therefore will start a new coroutine in another context
                withContext(NonCancellable) {
                    delay(1000) // call suspend function in withContext() {}
                    logger.info("Closing resource of withContextExample()")
                }
            }
        }

        delay(200)
        job.cancelAndJoin()

        logger.info("End of withContextExample() reached")
    }
}


fun customCancellationMessage() {
    val functionName = ::withContextExample.name
    var res = 0

    runBlocking {
        val job: Job = launch(Dispatchers.Default) {
            try {
                for (i in 1..100) {
                    print("$i-$res ")
                    res += i
                    delay(10)
                }
            } catch (e: CancellationException) {
                println()

                // print the custom cancellation message through the caught exception
                logger.info("Exception caught in $functionName: ${e.javaClass.simpleName}; message: ${e.message}")
            } finally {
                withContext(NonCancellable) {
                    delay(1000)
                    logger.info("Closing resource of $functionName")
                }
            }
        }

        delay(200)
        // pass the custom cancellation message
        job.cancel(CancellationException("Custom cancellation message here!"))
        job.join()

        logger.info("End of $functionName reached")
    }
}
