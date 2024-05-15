package com.example.androidReferences.apps.loginApp.di

import android.content.Context
import com.example.androidReferences.apps.loginApp.data.UsersDatabase
import com.example.androidReferences.apps.loginApp.ui.repository.UsersRepository


interface LoginRegisterModule {
    val repository: UsersRepository
}

class LoginRegisterModuleImpl(
    private val appContext: Context
) : LoginRegisterModule {
    private lateinit var db: UsersDatabase

    override val repository by lazy {
        UsersRepository(
            userDao = db.userDao()
        )
    }

    fun initialize() {
        db = UsersDatabase.getDatabase(appContext)
    }
}
