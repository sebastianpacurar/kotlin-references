package com.example.androidReferences.loginApp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidReferences.loginApp.LoginGraph
import com.example.androidReferences.loginApp.data.models.User
import com.example.androidReferences.loginApp.ui.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


interface RegisterViewModel {
    val user: StateFlow<String>
    val pass: StateFlow<String>

    fun register(user: String, pass: String, callback: (Boolean) -> Unit)
    fun setUser(value: String)
    fun setPass(value: String)
    fun resetFields()
}


class RegisterViewModelImpl(
    private val repository: Repository = LoginGraph.repository
) : RegisterViewModel, ViewModel() {

    private val _user = MutableStateFlow("")
    override val user: StateFlow<String>
        get() = _user.asStateFlow()

    private val _pass = MutableStateFlow("")
    override val pass: StateFlow<String>
        get() = _pass.asStateFlow()


    override fun register(user: String, pass: String, callback: (Boolean) -> Unit) {
        // bound to the lifecycle of RegisterViewModel
        viewModelScope.launch { // by default, dispatched to a background thread or thread pool (not the main thread) for execution
            // runs asynchronously - insert a user in the db
            val existingUser = repository.getUser(user, pass).firstOrNull()
            if (existingUser == null) { // if user does not exist
                // If user does not exist, insert the new user, and set callback to true
                repository.insert(User(0, user, pass))
                callback(true)
            } else { // if user exists
                // if user exists throw False
                callback(false)
            }
        }
    }

    override fun setUser(value: String) {
        _user.value = value
    }

    override fun setPass(value: String) {
        _pass.value = value
    }

    override fun resetFields() {
        _user.value = ""
        _pass.value = ""
    }
}
