package com.example.androidReferences.loginApp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "user_name")
    val name: String,

    @ColumnInfo(name = "user_pass")
    val password: String,

    @ColumnInfo(name = "is_logged")
    val isOnline: Boolean = false,

    @ColumnInfo(name = "is_admin")
    val isAdmin: Boolean = false,

    @ColumnInfo(name = "user_address")
    val address: String,

    @ColumnInfo(name = "user_number")
    val number: String,

    @ColumnInfo(name = "user_continent")
    val continent: String

)
