package com.example.androidReferences.apps.retrofitApp.presentation.allCountries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidReferences.apps.retrofitApp.domain.repository.CountriesRepository
import com.example.androidReferences.apps.retrofitApp.util.Event
import com.example.androidReferences.apps.retrofitApp.util.EventBus.sendEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CountriesViewModel(
    private val countriesRepository: CountriesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CountriesViewState())
    val state = _state.asStateFlow()

    fun getCountries() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            countriesRepository.getCountries()
                .onRight { countries ->
                    _state.update {
                        it.copy(countries = countries)
                    }
                }

                .onLeft { error ->
                    _state.update {
                        it.copy(error = error.error.message)
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }
}
