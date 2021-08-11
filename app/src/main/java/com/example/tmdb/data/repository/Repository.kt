package com.example.tmdb.data.repository

import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.repositories.GenreRepository
import com.example.tmdb.data.remote.repositories.MovieRepository

class Repository(
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository
) {
    suspend fun getMovieDetails(id: Int, api_key: String): Movie =
        movieRepository.getDetails(id, api_key)

    suspend fun getPopularMovies(api_key: String): List<Movie> =
        movieRepository.getPopular(api_key)

    suspend fun getSimilarMovies(id: Int, api_key: String): List<Movie> =
        movieRepository.getSimilar(id, api_key)

    suspend fun searchMovies(query: String, api_key: String) : List<Movie> =
        movieRepository.search(query, api_key)

    suspend fun discoverMovies(genre_ids: String, api_key: String) : List<Movie> =
        movieRepository.discover(genre_ids, api_key)

    suspend fun getGenres(api_key: String): List<Genre> = genreRepository.getGenres(api_key)
}