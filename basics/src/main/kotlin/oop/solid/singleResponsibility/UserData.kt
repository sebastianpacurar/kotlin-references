package oop.solid.singleResponsibility

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)
