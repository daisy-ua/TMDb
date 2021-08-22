package com.example.tmdb.data.remote.repositories

interface RemoteDataSource<T> {

    suspend fun getAll() : List<T>
}