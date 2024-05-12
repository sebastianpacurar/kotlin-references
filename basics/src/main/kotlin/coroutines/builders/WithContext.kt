package coroutines.builders

import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/*

  - withContext{} coroutine builder is used to change the context of the coroutine temporarily
    - it switches the coroutine's context to the specified dispatcher, such as Dispatchers.IO for I/O-bound tasks
    - this is particularly useful when you need to execute a block of code in a different context without launching a new coroutine

*/


fun main() {
    basicExample()
    performGetPostRequests()
}


fun basicExample() {
    var res = 0
    runBlocking {
        val msg = async { beginningMessage() }
        println(msg.await())

        withContext(Dispatchers.IO) {
            repeat(10) {
                delay(50L)
                res += it
                println("$it - $res")
            }
        }
    }
}


suspend fun beginningMessage(): String {
    delay(1000L)
    return "Starts repeating count"
}



//  withContext{} is used inside a runBlocking{} block to switch to Dispatchers.IO context for performing I/O operations
//  inside the withContext{} block, the code is executed with the context of Dispatchers.IO, allowing for concurrent I/O operations
//  once the withContext{} block finishes execution, the coroutine resumes in its original context
fun performGetPostRequests() = runBlocking {

    val fetchedData = withContext(Dispatchers.IO) { getFromApi() }
    println("Fetched Data: $fetchedData")

    val responseData = "Some data to post"
    val response = withContext(Dispatchers.IO) { postToApi(responseData) }
    println("Response after posting data: $response")
}


suspend fun getFromApi(): String {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://jsonplaceholder.typicode.com/posts/1")
        .build()

    delay(500L)

    return try {
        val response = client.newCall(request).execute()
        response.body?.string() ?: "No data received"
    } catch (e: IOException) {
        "Error fetching data: ${e.message}"
    }
}


suspend fun postToApi(data: String): String {
    val client = OkHttpClient()

    val requestBody = FormBody.Builder()
        .add("data", data)
        .build()

    val request = Request.Builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .post(requestBody)
        .build()

    delay(500L)

    return try {
        val response = client.newCall(request).execute()
        response.body?.string() ?: "No response received"
    } catch (e: IOException) {
        "Error posting data: ${e.message}"
    }
}


