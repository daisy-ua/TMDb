package com.example.tmdb.data.local.repository

import kotlinx.coroutines.flow.Flow

interface LocalDataSource<in Key: Any, T> {

    fun getAll() : Flow<List<T>>

    fun getById(vararg ids: Int) : Flow<List<T>>

    suspend fun insert(list: List<T>)

    suspend fun deleteAll()
}