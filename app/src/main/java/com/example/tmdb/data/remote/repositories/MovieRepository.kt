package com.example.tmdb.data.remote.repositories

import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.services.MovieServices
import com.example.tmdb.data.remote.models.MovieDTOMapper

// TODO: pass api_key via interceptor
class MovieRepository(
    private val movieService: MovieServices,
    private val mapper: MovieDTOMapper
) : IServicesRepository<Movie> {
    override suspend fun getDetails(id: Int, api_key: String): Movie =
        mapper.mapToDomainModel(movieService.getMovieDetails(id, api_key))

    override suspend fun search(query: String, api_key: String): List<Movie> =
        mapper.toDomainList(movieService.searchMovies(query, api_key).results)

    override suspend fun discover(genre_ids: String, api_key: String): List<Movie> =
        mapper.toDomainList(movieService.discoverMovies(genre_ids, api_key).results)

    override suspend fun getPopular(api_key: String): List<Movie> =
        mapper.toDomainList(movieService.getPopularMovies(api_key).results)

    override suspend fun getSimilar(id: Int, api_key: String): List<Movie> =
        mapper.toDomainList(movieService.getSimilarMovies(id, api_key).results)
}