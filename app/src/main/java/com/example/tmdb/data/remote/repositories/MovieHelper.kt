package com.example.tmdb.data.remote.repositories

import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.services.MovieServices
import com.example.tmdb.data.remote.models.MovieDTOMapper

class MovieHelper(
    private val movieService: MovieServices,
    private val mapper: MovieDTOMapper
) : IServicesRepository<Movie> {
    override suspend fun getDetails(id: Int): Movie =
        mapper.mapToDomainModel(movieService.getMovieDetails(id))

    override suspend fun search(query: String): List<Movie> =
        mapper.toDomainList(movieService.searchMovies(query).results)

    override suspend fun discover(genre_ids: String): List<Movie> =
        mapper.toDomainList(movieService.discoverMovies(genre_ids).results)

    override suspend fun getPopular(): List<Movie> =
        mapper.toDomainList(movieService.getPopularMovies().results)

    override suspend fun getSimilar(id: Int): List<Movie> =
        mapper.toDomainList(movieService.getSimilarMovies(id).results)
}