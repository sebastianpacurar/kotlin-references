package oop.solid.liskovSubstitution

import okhttp3.*
import java.io.IOException


/*
    Liskov-Substitution Principle states that:
    - objects of a superclass should be replaceable with objects of its subclasses
    - functioning of the program should not be affected by replacement
    - it enhances code reusability

*/


// bad hierarchy design, since both Http and Websocket are clients but with different attributions - breaks principle
abstract class Client {
    abstract fun performGet(httpAddress: String): String?
    abstract fun performPost(postEntity: PostEntity)
    abstract fun connectToSocket(socketAddress: String)
    abstract fun disconnectFromSocket(code: Int, reason: String?)
}

class HttpClientViolation : Client() {
    private val client = OkHttpClient()

    override fun performGet(httpAddress: String): String? {
        println("Perform GET Request:")
        val request = Request.Builder().url(httpAddress).build()
        val response = client.newCall(request).execute()
        return response.body?.string()
    }

    override fun performPost(postEntityData: PostEntity) {
        println("\nPerform POST Request:")
        val requestBody = FormBody.Builder()
            .add("title", postEntityData.title)
            .add("body", postEntityData.body)
            .add("userId", postEntityData.userId.toString())
            .build()
        val request = Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to perform  post request: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                println("Response code: ${response.code}")
                val responseBody = response.body?.string()
                println("Response body: $responseBody")
            }
        })
    }

    // the below methods need to be overridden, although they don't exist for HTTP clients - breaks principle
    override fun connectToSocket(socketAddress: String) {
        throw RuntimeException("HTTP Client does not support socket connection")
    }

    override fun disconnectFromSocket(code: Int, reason: String?) {
        throw RuntimeException("HTTP Client does not support socket disconnection")
    }
}

class WebSocketClientViolation : Client() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    override fun connectToSocket(socketAddress: String) {
        val request = Request.Builder().url(socketAddress).build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                println("\nPerform Websocket connection:")
                println("Connected to WebSocket")
                webSocket.send("Hello, from Liskov Violation!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                println("Received: $text")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                println("Websocket closed manually with code $code")
            }
        }
        webSocket = client.newWebSocket(request, listener)
    }

    override fun disconnectFromSocket(code: Int, reason: String?) {
        webSocket?.let {
            it.close(code, reason)
            webSocket = null
        }
    }

    // the below methods need to be overridden, although they don't exist for WebSocket clients - breaks principle
    override fun performGet(httpAddress: String): String? {
        throw RuntimeException("Web Socket does not support GET request")
    }

    override fun performPost(postEntity: PostEntity) {
        throw RuntimeException("Web Socket does not support POST request")
    }
}

fun main() {
    val postData = PostEntity(title = "My Post", body="My Post Content", userId=90)

    // instances are created and used interchangeably, although Liskov-Substitution is violated
    val httpClient = HttpClientViolation()
    val socketClient = WebSocketClientViolation()

    println(httpClient.performGet("https://jsonplaceholder.typicode.com/users/1"))
    httpClient.performPost(postData)

    Thread.sleep(1000)
    socketClient.connectToSocket("wss://echo.websocket.org")
    socketClient.disconnectFromSocket(4999, null)
}
