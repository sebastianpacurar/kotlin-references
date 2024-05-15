package com.example.androidReferences.loginApp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidReferences.ReferenceApp
import com.example.androidReferences.apps.loginApp.ui.repository.UsersRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


interface LoginViewModel {
    val user: StateFlow<String>
    val pass: StateFlow<String>
    val repo: UsersRepository

    fun login(user: String, password: String, callback: (Boolean) -> Unit)
    fun setUser(value: String)
    fun setPass(value: String)
    fun resetFields()
}


class LoginViewModelImpl : LoginViewModel, ViewModel() {
    override val repo = ReferenceApp.loginApp.repository

    private val _user = MutableStateFlow("")
    override val user: StateFlow<String>
        get() = _user.asStateFlow()

    private val _pass = MutableStateFlow("")
    override val pass: StateFlow<String>
        get() = _pass.asStateFlow()

    override fun setUser(value: String) {
        _user.value = value
    }

    override fun setPass(value: String) {
        _pass.value = value
    }

    override fun login(user: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            var success = false
            if (user.isNotBlank() && password.count() >= 2) {
                if (repo.getUser(user, password).firstOrNull() != null) {
                    repo.setLogStatus(user, true)
                    success = true
                }
            }
            callback(success)
        }
    }

    override fun resetFields() {
        _user.value = ""
        _pass.value = ""
    }
}
