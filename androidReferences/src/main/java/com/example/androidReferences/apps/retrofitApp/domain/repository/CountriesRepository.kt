package com.example.androidReferences.apps.retrofitApp.domain.repository

import arrow.core.Either
import com.example.androidReferences.apps.retrofitApp.domain.models.Country
import com.example.androidReferences.apps.retrofitApp.domain.models.NetworkError

interface CountriesRepository {
    suspend fun getCountries(): Either<NetworkError, List<Country>>
}
