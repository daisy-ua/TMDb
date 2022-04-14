package com.tmdb.repository.utils

sealed class Response<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T) : Response<T>(data)
    class Loading<T>(data: T? = null) : Response<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Response<T>(data, throwable)
}
