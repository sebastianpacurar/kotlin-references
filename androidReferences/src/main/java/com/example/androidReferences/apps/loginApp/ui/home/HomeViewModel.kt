package com.example.androidReferences.apps.loginApp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidReferences.ReferenceApp
import com.example.androidReferences.apps.loginApp.data.models.User
import com.example.androidReferences.apps.loginApp.ui.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


interface HomeViewModel {
    val currUser: StateFlow<String>
    val users: StateFlow<List<User>>
    val repo: UsersRepository

    fun setUsers(name: String)
    fun setCurrUser(name: String)
    fun logout(user: String)
}


class HomeViewModelImpl : HomeViewModel, ViewModel() {
    override val repo = ReferenceApp.loginApp.repository

    private val _currUser = MutableStateFlow("")
    override val currUser: StateFlow<String>
        get() = _currUser.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    override val users: StateFlow<List<User>>
        get() = _users.asStateFlow()


    override fun setUsers(name: String) {
        viewModelScope.launch {
            _users.value = repo.getUsersExcept(name).firstOrNull()!!
        }
    }

    override fun setCurrUser(name: String) {
        _currUser.value = name
    }

    override fun logout(user: String) {
        viewModelScope.launch {
            repo.setLogStatus(user, false)
        }
    }
}
