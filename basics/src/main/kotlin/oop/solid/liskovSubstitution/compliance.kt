package oop.solid.liskovSubstitution

import okhttp3.*
import java.io.IOException


/*
    Liskov-Substitution Principle states that:
    - objects of a superclass should be replaceable with objects of its subclasses
    - functioning of the program should not be affected by replacement
    - it enhances code reusability

*/


// abstract superclass for easier hierarchy design
abstract class WebClient

/* GET and POST are separated from Websocket
    performGet() and performPost() are declared with appropriate signatures for HTTP based operations
* */
abstract class HttpClient : WebClient() {
    abstract fun performGet(httpAddress: String): String?
    abstract fun performPost(postEntity: PostEntity)
}

/* connect and disconnect to/from socket are separated from oop.solid.dependencyInversion.HttpClient
    connectToSocket() and disconnectFromSocket() are declared with appropriate signatures for Websocket based operations
* */
abstract class WebSocketClient : WebClient() {
    abstract fun connectToSocket(socketAddress: String)
    abstract fun disconnectFromSocket(code: Int, reason: String?)
}

// concrete HttpClientImpl override the methods in its respective abstract class
class HttpClientImpl : HttpClient() {
    private val client = OkHttpClient()

    // used only for oop.solid.dependencyInversion.HttpClient
    override fun performGet(httpAddress: String): String? {
        println("Perform GET Request:")
        val request = Request.Builder().url(httpAddress).build()
        val response = client.newCall(request).execute()
        return response.body?.string()
    }

    // used only for oop.solid.dependencyInversion.HttpClient
    override fun performPost(postEntity: PostEntity) {
        println("\nPerform POST Request:")
        val requestBody = FormBody.Builder()
            .add("title", postEntity.title)
            .add("body", postEntity.body)
            .add("userId", postEntity.userId.toString())
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
}

// concrete WebSocketImpl override the methods in its respective abstract class
class WebSocketClientImpl : WebSocketClient() {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    // used only for WebsocketClient
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

    // used only for WebsocketClient
    override fun disconnectFromSocket(code: Int, reason: String?) {
        webSocket?.let {
            it.close(code, reason)
            webSocket = null
        }
    }
}

fun main() {
    val postData = PostEntity(title = "My Post", body = "My Post Content", userId = 90)

    // instances are created and used interchangeably
    val httpClient = HttpClientImpl()
    val socketClient = WebSocketClientImpl()

    println(httpClient.performGet("https://jsonplaceholder.typicode.com/users/1"))
    httpClient.performPost(postData)

    Thread.sleep(1000)
    socketClient.connectToSocket("wss://echo.websocket.org")
    socketClient.disconnectFromSocket(4999, null)
}

