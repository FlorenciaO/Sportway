package com.example.sportway.common

sealed class Resource<T>(val data: T? = null, val errorCode: ErrorCode? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(errorCode: ErrorCode, data: T? = null): Resource<T>(data, errorCode)
    class Loading<T>(data: T? = null): Resource<T>(data)
}
