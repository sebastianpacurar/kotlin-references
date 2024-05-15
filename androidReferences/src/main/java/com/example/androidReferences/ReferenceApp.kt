package com.example.androidReferences

import android.app.Application
import com.example.androidReferences.apps.loginApp.di.LoginRegisterModule
import com.example.androidReferences.apps.loginApp.di.LoginRegisterModuleImpl
import com.example.androidReferences.apps.retrofitApp.di.RetrofitAppModule
import com.example.androidReferences.apps.retrofitApp.di.RetrofitAppModuleImpl


class ReferenceApp : Application() {
    companion object {
        lateinit var loginApp: LoginRegisterModule
        lateinit var retrofitApp: RetrofitAppModule
    }

    override fun onCreate() {
        super.onCreate()
        loginApp = LoginRegisterModuleImpl(appContext = this).apply {
            initialize()
        }
        retrofitApp = RetrofitAppModuleImpl(appContext = this)
    }
}
