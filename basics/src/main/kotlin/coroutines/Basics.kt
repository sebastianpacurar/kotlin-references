package coroutines

import kotlin.concurrent.thread
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


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
fun threadExample() {
    val functionName = ::threadExample.name

    // called from the scope of the main function
    println("App starts in function: $functionName on thread: ${Thread.currentThread().name}")

    thread { // creates a background thread
        //  does not block the code of the main thread, running in parallel with the main thread
        println("Start Process in function: $functionName on thread: ${Thread.currentThread().name}")
        Thread.sleep(1000)
        println("Finish process in function: $functionName on thread: ${Thread.currentThread().name}")
    }

    println("App ends in function: $functionName on thread: ${Thread.currentThread().name}")
}


// the application does not wait for a coroutine to finish
fun coroutineExample() {
    val functionName = ::coroutineExample.name

    println("App starts in function: $functionName on thread: ${Thread.currentThread().name}")

    GlobalScope.launch { // creates a background coroutine that runs (operates) within a thread
        println("Start process in function: $functionName on thread: ${Thread.currentThread().name}")
        Thread.sleep(1000)
        println("Finish process in function: $functionName on thread: ${Thread.currentThread().name}")
    }

    Thread.sleep(2000)  // block the main thread, and wait for the coroutine to finish (an impractical way to wait)
    println("App ends in function: $functionName on thread: ${Thread.currentThread().name}")
}


/*
    delay() is a suspend function, meaning that it suspends the coroutine for a specified amount of time without blocking the main thread
      the suspending functions are only allowed to be called from a coroutine or from another suspending function
      they cannot be called from outside a coroutine
    runBlocking() is a coroutine builder which creates a coroutine that blocks the main thread until the coroutine completes its execution
    GlobalScope.launch() is non-blocking
* */
fun delayExample() {
    val functionName = ::coroutineExample.name

    println("App starts in function: $functionName on thread: ${Thread.currentThread().name}")

    GlobalScope.launch {
        println("Start Process in function: $functionName on thread: ${Thread.currentThread().name}")
        delay(1000) // coroutine is suspended but Thread is not blocked
        println("Finish Process in function: $functionName on thread: ${Thread.currentThread().name}")
    }

    // using delay outside the runBlocking {} will not work
    runBlocking { // creates a coroutine tha blocks the current main thread
        delay(2000)
    }

    println("App ends in function: $functionName on thread: ${Thread.currentThread().name}")
}


/*
    Sequential steps:
    (1) function called from main() function which starts to execute in the main thread
    (2) variable functionName is assigned the name of the current function in the main thread
    (3) runBlocking() launches a new coroutine that blocks the main thread, meaning that this coroutine runs on the main thread
    (4) the first print statement runs on the main thread, because it lies within the scope of runBlocking method, which is running on the main thread
    (5) GlobalScope.launch() creates a child coroutine in a background thread, meaning that its content starts executing concurrently in the background
    (6) delay(2000) is present within the scope of runBlocking, meaning it executes in the main thread
    (7) the last print statement runs on the main thread
* */
fun runBlockingExample() { // (1)
    val functionName = ::runBlockingExample.name // (2)

    runBlocking { // (3)

        println("App starts in function: $functionName on thread: ${Thread.currentThread().name}") // (4)

        GlobalScope.launch { // executes on T1 // (5)
            println("Start Process in function: $functionName on thread: ${Thread.currentThread().name}") // T1
            delay(1000) // coroutine is suspended but T1 is free (not blocked)
            println("Finish process in function: $functionName on thread: ${Thread.currentThread().name}") // either T1 or some other thread
        }

        delay(2000) // (6)

        println("App ends in function: $functionName on thread: ${Thread.currentThread().name}") // (7)
    }
}

