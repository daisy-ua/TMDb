package com.example.tmdb.data.remote.repositories


interface RemoteAdvancedDataSource<T> : RemoteDataSource<T> {

    suspend fun getDetails(id: Int) : T

    suspend fun getSimilar(id: Int) : List<T>

    suspend fun search(query: String) : List<T>

    suspend fun discover(genre_ids: String) : List<T>
}