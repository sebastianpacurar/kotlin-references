package coroutines.contextAndScope

import kotlinx.coroutines.*


/*
    +-------------------------------------------------------------------------------------+
    | Difference             | ExplicitCoroutineScope    | CoroutineScopeBuilder          |
    +------------------------+---------------------------+--------------------------------+
    | Object Creation        | Explicitly created        | Created using coroutineScope{} |
    | Dispatcher             | Specific dispatcher       | Inherited context              |
    | Use Case               | Manages coroutine lifetime| Structured concurrency         |
    | Cancellation Handling  | Manual cancellation       | Automatic cancellation         |
    | Usage                  | Specific dispatcher needs | Structured scope usage         |
    +-------------------------------------------------------------------------------------+


*/

fun main() {
    println(" ExplicitCoroutineScope example")
    explicitCoroutineScope()

    println("\n CoroutineScopeBuilder example")
    coroutineScopeBuilder()
}

/*
* Explicit Coroutine Scope
* */

// explicitly creates a CoroutineScope object with a specific dispatcher
val scope = CoroutineScope(Dispatchers.Default)

fun explicitCoroutineScope() = runBlocking {
    val job = scope.launch {
        delay(100)
        println("Coroutine completed")
    }


    job.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine was cancelled")
        }
    }

    delay(50)
    onDestroy()
}


fun onDestroy() {
    println("life-time of scope ends")
    scope.cancel()
}


/*
* CoroutineScope Builder
* */

fun coroutineScopeBuilder() = runBlocking {
    coroutineScope {
        // Launch two coroutines in parallel
        val job1 = launch {
            delay(100)
            println("Coroutine 1 completed")
        }

        val job2 = launch {
            delay(200)
            println("Coroutine 2 completed")
        }

        // Join both coroutines to wait for their completion
        job1.join()
        job2.join()

        // All coroutines launched in this scope have completed
        println("All coroutines completed")
    }

    // Outside the coroutineScope block, all coroutines have completed
    println("CoroutineScope has completed")
}
