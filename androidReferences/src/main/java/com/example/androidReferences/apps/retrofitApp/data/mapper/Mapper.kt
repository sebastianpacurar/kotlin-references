package com.example.androidReferences.apps.retrofitApp.data.mapper

import com.example.androidReferences.apps.retrofitApp.domain.models.ApiError
import com.example.androidReferences.apps.retrofitApp.domain.models.NetworkError
import retrofit2.HttpException
import java.io.IOException


fun Throwable.toNetworkError(): NetworkError {
    val error = when(this) {
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownError
    }

    return NetworkError(error = error)
}
