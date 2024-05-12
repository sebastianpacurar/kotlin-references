package coroutines.builders

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/*
  - generally, runBlocking{} coroutine builder is used to write test cases to test suspension functions
*/


class MyTest {
    private val logger: Logger = LoggerFactory.getLogger("coroutines.builders.runBlocking")

    @Test
    fun testPassingAddition() = runBlocking { // usage of runBlocking inside junit test
        assertEquals(passingTest(this), 4) // passing the runBlocking{} scope as param to the test
        logger.info("Test testPassingAddition passed.")
    }

    @Test
    fun testFailingAddition() = runBlocking { // usage of runBlocking inside junit test
        try {
            val result = failingTest(this)
            assertEquals(11, result)  // will fail here since expected is 11 while returned is 10
            logger.info("Test testFailingAddition passed. Result: $result")
        } catch (e: AssertionError) {
            logger.error("Test testFailingAddition failed: ${e.message}")
            throw e
        }
    }
}


// passing test
suspend fun passingTest(scope: CoroutineScope): Int {
    val addition: Deferred<Int> = scope.async {
        delay(1000L)
        2 + 2
    }
    return addition.await()
}


// failing test
suspend fun failingTest(scope: CoroutineScope): Int {
    val addition: Deferred<Int> = scope.async {
        delay(1000L)
        5 + 5
    }
    return addition.await()
}
