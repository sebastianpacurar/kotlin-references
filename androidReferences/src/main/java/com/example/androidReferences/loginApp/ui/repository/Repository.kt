package com.example.androidReferences.loginApp.ui.repository

import com.example.androidReferences.loginApp.data.UserDao
import com.example.androidReferences.loginApp.data.models.User


// acts as an abstraction layer between the ViewModel and the data source
class Repository(
    private val userDao: UserDao,
) {
    fun getUser(name: String, pass: String) = userDao.getUser(name, pass)
    fun getUsersExcept(name: String) = userDao.getUsersExcept(name)
    suspend fun insert(user: User) = userDao.insert(user)
    suspend fun setLogStatus(name: String, status: Boolean) = userDao.setLogStatus(name, status)
}
