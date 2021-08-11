package com.example.tmdb.data.remote.repositories


interface IServicesRepository<T> {
    suspend fun getPopular(api_key: String) : List<T>

    suspend fun getDetails(id: Int, api_key: String) : T

    suspend fun getSimilar(id: Int, api_key: String) : List<T>

    suspend fun search(query: String, api_key: String) : List<T>

    suspend fun discover(genre_ids: String, api_key: String) : List<T>
}