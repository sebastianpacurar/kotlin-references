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
    val isLogged: Boolean = false,

    @ColumnInfo(name = "is_admin")
    val isAdmin: Boolean = false
)
