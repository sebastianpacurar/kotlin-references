package oop.solid.dependencyInversion


data class PostEntity(
    val title: String,
    val body: String,
    val userId: Int
)

data class ProductEntity(
    val title: String,
    val price: Double,
    val description: String,
    val image: String,
    val category: String
)
