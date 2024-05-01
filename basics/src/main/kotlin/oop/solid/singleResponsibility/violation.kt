package oop.solid.singleResponsibility

import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


/*
    Single-Responsibility Principle states that:
    - a class/function/module should have one reason to change
    - functionality becomes isolated
    - fewer dependencies per class, therefore less coupling

*/


class PrincipleViolation(
    // multiple dependencies, therefore the class has multiple reasons to change - breaks principle
    private val client: OkHttpClient,
    private val url: String,
) {
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchData(): List<UserData>? {
        // first responsibility: fetch data from the provided url
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        // second responsibility: parse data UserData - breaks principle
        return if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            // this functionality is tightly coupled to the GET request - breaks principle
            json.decodeFromString<List<UserData>>(responseBody)
        } else {
            println("Failed to fetch data from $url")
            null
        }
    }

    // third responsibility: print users list - breaks principle
    fun printUserList(userList: List<UserData>?) {
        userList?.forEach { user ->
            println("Name: ${user.name}, Username: ${user.username}, Email: ${user.email}, Phone: ${user.phone}, Website: ${user.website}")
        }
    }
}

fun main() {
    val dataManager = PrincipleViolation(OkHttpClient(), "https://jsonplaceholder.typicode.com/users")
    val userList = dataManager.fetchData()
    dataManager.printUserList(userList)
}
