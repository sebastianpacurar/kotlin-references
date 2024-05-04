package oop.solid.dependencyInversion

import okhttp3.*

/*
    Dependency-Inversion Principle states that:
    - high-level modules should not depend on low-level modules
    - abstractions should not depend on details
    - changes in one part of the system won't have an effect on others
*/


// interfaces show clear separation between the abstract behavior and the concrete implementation

interface RequestBuilder {
    fun buildGetRequest(url: String): Request
    fun buildPostRequest(url: String, requestBody: RequestBody): Request
}


interface HttpClient {
    fun executeRequest(request: Request): Response
}


interface DataFetcher {
    fun fetchDataGet(url: String): String
    fun fetchDataPost(url: String, requestBody: RequestBody): String
}

/*
  RequestBuilderImpl represents a low-level module - it is related to building requests using OkHttp library
   it directly interacts with the library, to build the GET and the POST requests
* */
class RequestBuilderImpl : RequestBuilder {
    override fun buildGetRequest(url: String): Request {
        return Request.Builder().url(url).build()
    }

    override fun buildPostRequest(url: String, requestBody: RequestBody): Request {
        return Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
    }
}

/*
  HttpClientImpl represents a low-level module - it is related to making requests using OkHttp library
    it directly interacts with the library, to execute the created requests
* */
class HttpClientImpl : HttpClient {
    private val client = OkHttpClient()

    override fun executeRequest(request: Request): Response {
        return client.newCall(request).execute()
    }
}


/*
  DataFetcherImpl represents a higher-level module - does not depend on an implementation of HttpClient nor RequestBuilder
    instead it depends on their interfaces, abstracting away the details of implementing the client or request builder
    this way changes in the low-level modules (ex, change the library from OkHttp to something else) won't affect DataFetcherImpl
* */
class DataFetcherImpl(
    private val httpClient: HttpClient,
    private val requestBuilder: RequestBuilder
) : DataFetcher {
    override fun fetchDataGet(url: String): String {
        val request = httpClient.executeRequest(requestBuilder.buildGetRequest(url))
        return request.body?.string() ?: ""
    }

    override fun fetchDataPost(url: String, requestBody: RequestBody): String {
        val request = httpClient.executeRequest(requestBuilder.buildPostRequest(url, requestBody))
        return request.body?.string() ?: ""
    }
}


fun main() {
    val postEntity = PostEntity(title = "My Post", body = "My Post Content", userId = 90)
    val productEntity = ProductEntity(
        title = "My Product",
        price = 13.5,
        description = "Product Description",
        image = "https://i.pravatar.cc/300",
        category = "Some category"
    )

    val requestBuilder: RequestBuilder = RequestBuilderImpl()
    val httpClient: HttpClient = HttpClientImpl()
    val dataFetcher: DataFetcher = DataFetcherImpl(httpClient, requestBuilder)

    // GET from jsonplaceholder api
    val getPostEntity = dataFetcher.fetchDataGet("https://jsonplaceholder.typicode.com/posts/1")
    println("Data for one post from JSONPlaceholder API (GET): $getPostEntity")

    // POST to jsonplaceholder api
    val postData = FormBody.Builder()
        .add("title", postEntity.title)
        .add("body", postEntity.body)
        .add("userId", postEntity.userId.toString())
        .build()
    val postPostEntity = dataFetcher.fetchDataPost("https://jsonplaceholder.typicode.com/posts", postData)
    println("\nData from JSONPlaceholder API (POST): $postPostEntity")

    // GET from fakestoreapi
    val getProduct = dataFetcher.fetchDataGet("https://fakestoreapi.com/products/1")
    println("\nData for one product from Fake Store API (GET): $getProduct")

    // POST to fakestoreapi
    val productData = FormBody.Builder()
        .add("title", productEntity.title)
        .add("price", productEntity.price.toString())
        .add("description", productEntity.description)
        .add("image", productEntity.image)
        .add("category", productEntity.category)
        .build()
    val postProductEntity = dataFetcher.fetchDataPost("https://fakestoreapi.com/products", productData)
    println("\nData from Fake Store API (POST): $postProductEntity")
}
