package com.example.androidReferences.loginApp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidReferences.loginApp.LoginGraph
import com.example.androidReferences.loginApp.ui.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


interface LoginViewModel {
    val user: StateFlow<String>
    val pass: StateFlow<String>

    fun login(user: String, password: String, callback: (Boolean) -> Unit)
    fun setUser(value: String)
    fun setPass(value: String)
    fun resetFields()
}

class LoginViewModelImpl(
    private val repository: Repository = LoginGraph.repository
) : LoginViewModel, ViewModel() {

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
            val userFlow = repository.getUser(user, password)
            val res = userFlow.firstOrNull()
            // True if the user exists in the db
            callback(res != null)
        }
    }

    override fun resetFields() {
        _user.value = ""
        _pass.value = ""
    }
}
