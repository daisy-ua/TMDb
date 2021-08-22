package com.example.tmdb.data.local.repository

import com.example.tmdb.data.models.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocalDataSource<in Key: Any, T> {

    fun getAll() : Flow<List<T>>

    suspend fun insert(list: List<T>)

    suspend fun deleteAll()
}