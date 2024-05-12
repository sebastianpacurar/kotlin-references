package coroutines.builders

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/*

  - timeouts can be handled using withTimeout() ot withTimeoutOrNull() coroutine builders:
    - withTimeout(<time>) throws a TimeoutCancellationException if the specified timeout is reached before the block of code completes
    - withTimeoutOrNull(<time>) returns null if the timeout is reached before the block of code completes, allowing for graceful handling of timeouts


*/

private val logger: Logger = LoggerFactory.getLogger("coroutines.builders.withTimeout")


fun main() {
    println(" withTimeoutOrNullReachedExample execution reached")
    withTimeoutOrNullReachedExample()

    println(" withTimeoutOrNullUnreachedExample execution reached")
    withTimeoutOrNullUnreachedExample()

    println("\n withTimeoutExample execution reached") // throws exception
    withTimeoutExample()
}


fun withTimeoutExample() = runBlocking {
    withTimeout(2000) {
        try {
            for (i in 1..500) {
                print("$i ")
                delay(500L)
            }
        } catch (e: TimeoutCancellationException) {
            // TimeoutCancellationException extends CancellationException
            //   throws exception when timeout is reached before the loop ends
            logger.info("Exception caught: ${e.javaClass.simpleName}")
        }
    }
}


fun withTimeoutOrNullReachedExample() {
    var res = 0

    runBlocking {
        val withTimeoutReachedResult: Int? = withTimeoutOrNull(2000) {
            for (i in 1..500) {
                logger.info("i = $i")
                res += i
                delay(500L)
            }
            res
        }

        logger.info("Timeout value: $withTimeoutReachedResult") // will be null since timeout is reached before the loop ends
        logger.info("Result value: $res\n")
    }

}


fun withTimeoutOrNullUnreachedExample() {
    var res = 0

    runBlocking {
        val withTimeoutUnreachedResult: Int? = withTimeoutOrNull(2000) {
            for (i in 1..50) {
                logger.info("i = $i")
                res += i
                delay(1)
            }
            res
        }

        logger.info("Timeout value: $withTimeoutUnreachedResult") // will be the final sum
        logger.info("Result value: $res")
    }
}
