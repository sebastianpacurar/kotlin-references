package com.example.androidReferences.loginApp

import android.content.Context
import com.example.androidReferences.loginApp.data.UsersDatabase
import com.example.androidReferences.loginApp.ui.repository.Repository


//singleton for managing Login App dependencies
object LoginGraph {
    private lateinit var db: UsersDatabase

    // when accessed for the first time, initialize Repository using the UserDatabase DAO
    val repository by lazy {
        Repository(
            userDao = db.userDao()
        )
    }

    // initialize UsersDatabase instance with a 'Context'
    //   allows external classes (ReferenceApp) to initialize the db within their context
    fun provide(context: Context) {
        db = UsersDatabase.getDatabase(context)
    }
}
