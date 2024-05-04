package oop.solid.openClosed

import java.net.URL


/*
    Open-Closed Principle states that:
    - classes should be open for extension, but closed for modification
    - you should be able to add new functionality without changing the existing one
    - classes are more decoupled

*/


class PrincipleViolation(
    private val dataType: DataType,
) {
    private val baseUrl = "https://jsonplaceholder.typicode.com"
    fun fetchData(): String {
        // in order to add new endpoints, this would mean to modify the current class - breaks principle
        // all the endpoints are tightly coupled, needing an extra enum class, or some sort of identification - breaks principle
        return when (dataType) {
            DataType.POST_DATA -> URL("$baseUrl/posts").readText()
            DataType.COMMENT_DATA -> URL("$baseUrl/comments").readText()
            DataType.ALBUM_DATA -> URL("$baseUrl/albums").readText()
            DataType.PHOTO_DATA -> URL("$baseUrl/photos").readText()
            DataType.TODO_DATA -> URL("$baseUrl/todos").readText()
            DataType.USER_DATA -> URL("$baseUrl/users").readText()
        }
    }
}

enum class DataType {
    POST_DATA,
    COMMENT_DATA,
    ALBUM_DATA,
    PHOTO_DATA,
    TODO_DATA,
    USER_DATA
}


fun main() {
    val dataManager = PrincipleViolation(DataType.TODO_DATA)
    val allData = dataManager.fetchData()
    println(allData)
}
