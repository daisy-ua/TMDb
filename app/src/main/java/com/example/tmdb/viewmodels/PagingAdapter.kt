package com.example.tmdb.viewmodels

import androidx.lifecycle.MutableLiveData
import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

interface PagingAdapter<T> {
    val result: MutableLiveData<Resource<List<T>>>

    var currentPage: Int

    suspend fun invokeNextPageLoading(flow: Flow<Resource<List<T>>>) {
        incrementPage()
        // TODO: check last page
        if (currentPage > 0) {
            flow
                .catch {  }
                .collect { response ->
                    when(response) {
                        is Resource.Success -> appendResults(response.data!!)
                        else -> {
//                            TODO("catch errors")
                        }
                    }
                }
        }
    }

    private fun appendResults(list: List<T>) {
        val current = ArrayList(result.value?.data ?: emptyList())
        current.addAll(list)
        result.value = Resource.Success(current)
    }

    private fun incrementPage() { currentPage += 1 }
}