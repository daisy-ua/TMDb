package com.example.tmdb.utils

import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.flow.*

inline fun <ResultType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> ResultType,
    crossinline saveFetchResult: suspend (ResultType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        val result = flow { emit(fetch()) }
        result.map { Resource.Success(it) }
    }
    emitAll(flow)
}