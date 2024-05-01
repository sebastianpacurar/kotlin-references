package oop.solid.singleResponsibility

import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


/*
    Single-Responsibility Principle states that:
    - a class/function/module should have one reason to change
    - functionality becomes isolated
    - fewer dependencies per class, therefore less coupling

*/


// DataManager is responsible only for grabbing data from the API
class DataManager(private val client: OkHttpClient) {
    fun fetchData(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            response.body?.string()
        } else {
            println("HTTP error: ${response.code}")
            null
        }
    }
}

// JsonParser is responsible only for parsing the response data to UserData structure
class JsonParser {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseUserDataList(jsonString: String): List<UserData>? {
        return try {
            json.decodeFromString<List<UserData>>(jsonString)
        } catch (e: SerializationException) {
            throw Exception("Error parsing JSON string: ${e.message}")
        }
    }
}

// UserPrinter is responsible only for printing data
class UserPrinter {
    fun printUserList(userList: List<UserData>?) {
        userList?.forEach { user ->
            println("Name: ${user.name}, Username: ${user.username}, Email: ${user.email}, Phone: ${user.phone}, Website: ${user.website}")
        } ?: println("The List of Users is null")
    }
}

fun main() {
    val client = OkHttpClient()
    val dataManager = DataManager(client)
    val jsonParser = JsonParser()
    val userPrinter = UserPrinter()

    val url = "https://jsonplaceholder.typicode.com/users"
    val json = dataManager.fetchData(url)
    val userList = jsonParser.parseUserDataList(json ?: "")

    userPrinter.printUserList(userList)
}
