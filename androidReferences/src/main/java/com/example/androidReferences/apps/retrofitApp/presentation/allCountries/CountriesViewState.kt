package com.example.androidReferences.apps.retrofitApp.presentation.allCountries

import com.example.androidReferences.apps.retrofitApp.domain.models.Country


data class CountriesViewState(
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val error: String? = null
)
