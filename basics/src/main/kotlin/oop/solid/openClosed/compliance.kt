package oop.solid.openClosed

import java.net.URL


/*
    Open-Closed Principle states that:
    - classes should be open for extension, but closed for modification
    - you should be able to add new functionality without changing the existing one
    - classes are more decoupled

*/


const val BASE_URL: String = "https://jsonplaceholder.typicode.com"

//  interface to highlight the functionality of each class
interface DataFetcher {
    fun fetchData(baseUrl: String): String
}

// related only to posts endpoint, implements oop.solid.dependencyInversion.DataFetcher
class PostsFetcher : DataFetcher {
    override fun fetchData(baseUrl: String): String {
        return URL("$BASE_URL/posts").readText()
    }
}

// related only to comments endpoint, implements oop.solid.dependencyInversion.DataFetcher
class CommentsFetcher : DataFetcher {
    override fun fetchData(baseUrl: String): String {
        return URL("$BASE_URL/comments").readText()
    }
}

// related only to albums, implements oop.solid.dependencyInversion.DataFetcher
class AlbumsFetcher : DataFetcher {
    override fun fetchData(baseUrl: String): String {
        return URL("$BASE_URL/albums").readText()
    }
}

// related only to photos endpoint, implements oop.solid.dependencyInversion.DataFetcher
class PhotosFetcher : DataFetcher {
    override fun fetchData(baseUrl: String): String {
        return URL("$BASE_URL/photos").readText()
    }
}

// related only to todos endpoint, implements oop.solid.dependencyInversion.DataFetcher
class TodosFetcher : DataFetcher {
    override fun fetchData(baseUrl: String): String {
        return URL("$BASE_URL/todos").readText()
    }
}

// related only to users endpoint, implements oop.solid.dependencyInversion.DataFetcher
class UsersFetcher : DataFetcher {
    override fun fetchData(baseUrl: String): String {
        return URL("$BASE_URL/users").readText()
    }
}


fun main() {
    /* baseurl could be changed, without modifying classes.
        examples of different BASE_URLs would be different environments, like testing, staging, pre-prod environments
     */
    val usersData = UsersFetcher().fetchData(BASE_URL)
    println(usersData)
}
