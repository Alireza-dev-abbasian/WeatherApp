package com.example.myapp.util.network

import com.example.myapp.data.enums.ErrorType


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val errorType: ErrorType? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null, errorType: ErrorType) :
        Resource<T>(data, message, errorType)

    class Loading<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
}