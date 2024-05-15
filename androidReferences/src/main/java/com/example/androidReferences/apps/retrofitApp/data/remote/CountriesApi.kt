package com.example.androidReferences.apps.retrofitApp.data.remote

import com.example.androidReferences.apps.retrofitApp.domain.models.Country
import retrofit2.http.GET


interface CountriesApi {
    @GET("all")
    suspend fun getCountries(): List<Country>
}
