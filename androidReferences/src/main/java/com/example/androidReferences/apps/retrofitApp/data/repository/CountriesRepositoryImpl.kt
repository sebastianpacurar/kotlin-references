package com.example.androidReferences.apps.retrofitApp.data.repository

import arrow.core.Either
import com.example.androidReferences.apps.retrofitApp.data.mapper.toNetworkError
import com.example.androidReferences.apps.retrofitApp.data.remote.CountriesApi
import com.example.androidReferences.apps.retrofitApp.domain.models.Country
import com.example.androidReferences.apps.retrofitApp.domain.models.NetworkError
import com.example.androidReferences.apps.retrofitApp.domain.repository.CountriesRepository


class CountriesRepositoryImpl(
    private val countriesApi: CountriesApi
) : CountriesRepository {
    override suspend fun getCountries(): Either<NetworkError, List<Country>> {
        return Either.catch {
            countriesApi.getCountries()
        }.mapLeft { it.toNetworkError() }
    }
}
