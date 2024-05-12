package coroutines.contextAndScope

import kotlinx.coroutines.*

/*

  - each coroutine has its own CoroutineScope instance attached to it, and each CoroutineScope has a CoroutineContext
  - a coroutine context will have a Job, a Dispatcher, and a CoroutineName.
  - the dispatcher determines the thread of a coroutine.
    - Dispatchers.Default: network requests, cpu-intensive tasks (image processing, data manipulation), file I/O operations, db operations
    - Dispatchers.Main: UI updates, UI animations, user input handling
    - Dispatchers.IO: Disk I/O operations, bg processing (file download), db access, long-running operations (image loading, video processing)
    - Dispatchers.Unconfined: immediate executions, propagation of coroutine context, intermittent suspension, temporary execution

*/


fun main() { // Thread: main
    runBlocking {
        /* without dispatcher parameter - confined coroutine (executes in the same thread)
         * - inherits CoroutineContext from immediate parent coroutine.
         * - continues to run in the same thread, regardless of suspending function execution.
         */
        launch { // Argument defaults to Dispatchers.Default
            println("C1 before: ${Thread.currentThread().name}") // Thread: main (parent)
            delay(100L)
            println("C1 after: ${Thread.currentThread().name}") // Thread: main
        }

        /* with Dispatchers.Default parameter (equivalent to GlobalScope.launch {})
        * - gets its own context at the Global level, executing in a separate background thread.
        * - may continue to run in the same thread or another thread after executing suspending functions.
        */
        launch(Dispatchers.Default) {
            println("C2 before: ${Thread.currentThread().name}")  // Thread: T1
            delay(100L)
            println("C2 after: ${Thread.currentThread().name}") // Thread: T1 or some other thread
        }

        /* with Dispatchers.Unconfined parameter
        * - inherits CoroutineContext from immediate parent coroutine.
        * - continues to run in a different thread after executing suspending functions.
        */
        launch(Dispatchers.Unconfined) {
            println("C3 before: ${Thread.currentThread().name}") // Thread: main
            delay(100L)
            println("C3 after: ${Thread.currentThread().name}") // Thread: some other thread

            // nested launch inherits parent's CoroutineContext
            launch(coroutineContext) {
                println("C4 before: ${Thread.currentThread().name}") // Thread: T1
                delay(100L)
                println("C4 after: ${Thread.currentThread().name}") // Thread: T1
            }
        }
    }
}
