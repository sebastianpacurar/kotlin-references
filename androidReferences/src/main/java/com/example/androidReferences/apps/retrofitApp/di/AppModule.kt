package com.example.androidReferences.apps.retrofitApp.di

import android.content.Context
import com.example.androidReferences.Constant.REST_ALL_COUNTRIES_URL
import com.example.androidReferences.apps.retrofitApp.data.remote.CountriesApi
import com.example.androidReferences.apps.retrofitApp.data.repository.CountriesRepositoryImpl
import com.example.androidReferences.apps.retrofitApp.domain.repository.CountriesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface RetrofitAppModule {
    val countriesApi: CountriesApi
    val countriesRepository: CountriesRepository
}

class RetrofitAppModuleImpl(
    private val appContext: Context
) : RetrofitAppModule {
    override val countriesApi: CountriesApi by lazy {
        Retrofit.Builder()
            .baseUrl(REST_ALL_COUNTRIES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }

    override val countriesRepository: CountriesRepository by lazy {
        CountriesRepositoryImpl(countriesApi)
    }
}
