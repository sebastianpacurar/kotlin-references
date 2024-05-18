package com.example.androidReferences.apps.loginApp.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidReferences.Constant.continents
import com.example.androidReferences.ReferenceApp
import com.example.androidReferences.apps.loginApp.data.models.User
import com.example.androidReferences.apps.loginApp.ui.repository.UsersRepository
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


interface RegisterViewModel {
    val user: StateFlow<String>
    val pass: StateFlow<String>
    val address: StateFlow<String>
    val number: StateFlow<String>
    val country: StateFlow<String>
    val continent: StateFlow<String>
    val repo: UsersRepository

    fun register(
        user: String,
        pass: String,
        address: String,
        continent: String,
        number: String,
        callback: (Boolean) -> Unit
    )

    fun setUser(value: String)
    fun setPass(value: String)
    fun setAddress(value: String)
    fun setNumber(value: String)
    fun setCountry(value: String)
    fun setContinent(value: String)
    fun setRandomData()
    fun resetFields()
}


class RegisterViewModelImpl : RegisterViewModel, ViewModel() {
    override val repo = ReferenceApp.loginApp.repository

    private val _user = MutableStateFlow("")
    override val user: StateFlow<String> = _user.asStateFlow()

    private val _pass = MutableStateFlow("")
    override val pass: StateFlow<String> = _pass.asStateFlow()

    private val _address = MutableStateFlow("")
    override val address: StateFlow<String> = _address.asStateFlow()

    private val _continent = MutableStateFlow("")
    override val continent: StateFlow<String> = _continent.asStateFlow()

    private val _country = MutableStateFlow("")
    override val country: StateFlow<String> = _country.asStateFlow()

    private val _number = MutableStateFlow("")
    override val number: StateFlow<String> = _number.asStateFlow()

    override fun register(
        user: String,
        pass: String,
        address: String,
        continent: String,
        number: String,
        callback: (Boolean) -> Unit
    ) {
        // bound to the lifecycle of RegisterViewModel
        viewModelScope.launch { // by default, dispatched to a background thread or thread pool (not the main thread) for execution
            var success = false
            if (user.isNotBlank()) {
                if (repo.getUser(user, pass).firstOrNull() == null) {
                    repo.insert(
                        User(
                            id = 0,
                            name = user,
                            password = pass,
                            address = address,
                            number = number,
                            continent = continent
                        )
                    ) //TODO: treat isAdmin differently
                    repo.setLogStatus(user, true)
                    success = true
                }
            }
            callback(success)
        }
    }

    override fun setUser(value: String) {
        _user.value = value
    }

    override fun setPass(value: String) {
        _pass.value = value
    }

    override fun setAddress(value: String) {
        _address.value = value
    }

    override fun setCountry(value: String) {
        _country.value = value
    }

    override fun setNumber(value: String) {
        _number.value = value
    }

    override fun setContinent(value: String) {
        _continent.value = value
    }

    override fun setRandomData() {
        _address.value = Faker().address.streetName()
        _number.value = Faker().address.buildingNumber()
        _user.value = Faker().name.firstName()
        _pass.value = Faker().name.lastName()
    }

    override fun resetFields() {
        _user.value = ""
        _pass.value = ""
        _address.value = ""
        _number.value = ""
        _continent.value = continents.first()
    }
}
