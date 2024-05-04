package oop.solid.dependencyInversion

import okhttp3.*

/*
    Dependency-Inversion Principle states that:
    - high-level modules should not depend on low-level modules
    - abstractions should not depend on details
    - changes in one part of the system won't have an effect on others
*/


interface RequestBuilderViolation {
    fun buildGetRequest(url: String): Request
    fun buildPostRequest(url: String, requestBody: RequestBody): Request
}


interface DataFetcherViolation {
    fun fetchDataGet(url: String): String
    fun fetchDataPost(url: String, requestBody: RequestBody): String
}

interface HttpClientViolation {
    fun executeRequest(request: Request): Response
}


class HttpClientImplViolation : HttpClientViolation {
    private val client = OkHttpClient()

    override fun executeRequest(request: Request): Response {
        return client.newCall(request).execute()
    }
}


class RequestBuilderImplViolation : RequestBuilderViolation {
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


// RequestBuilderViolation and HttpClientViolation are not used in DataFetcherImplViolation class - breaks principle
class DataFetcherImplViolation : DataFetcherViolation {
    override fun fetchDataGet(url: String): String {
        // direct instantiation of concrete low-level module - breaks principle
        val requestBuilder = RequestBuilderImplViolation()

        // direct instantiation of concrete low-level module - breaks principle
        val httpClient = HttpClientImplViolation()

        val request = httpClient.executeRequest(requestBuilder.buildGetRequest(url))
        return request.body?.string() ?: ""
    }

    override fun fetchDataPost(url: String, requestBody: RequestBody): String {
        // direct instantiation of low-level module - breaks principle
        val requestBuilder = RequestBuilderImplViolation()

        // direct instantiation of low-level module - breaks principle
        val httpClient = HttpClientImplViolation()

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

    // direct instantiation of low-level module - breaks principle
    val dataFetcher = DataFetcherImplViolation()

    // fetching data for one post from JSONPlaceholder API
    val getPostEntity = dataFetcher.fetchDataGet("https://jsonplaceholder.typicode.com/posts/1")
    println("Data for one post from JSONPlaceholder API (GET): $getPostEntity")

    // fetching data from JSONPlaceholder API
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
