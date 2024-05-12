package com.example.androidReferences

import android.app.Application
import com.example.androidReferences.loginApp.LoginGraph


// starting point of the app
class ReferenceApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // initialize dependencies for the login functionality by providing the application context
        LoginGraph.provide(this)
    }
}
