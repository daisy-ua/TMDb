package com.tmdb.repository.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType> networkBoundResult(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> ResultType,
    crossinline saveFetchResult: suspend (ResultType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Response.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Response.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Response.Error(throwable, it) }
        }
    } else {
        val result = flow { emit(fetch()) }
        result.map { Response.Success(it) }
    }
    emitAll(flow)
}
