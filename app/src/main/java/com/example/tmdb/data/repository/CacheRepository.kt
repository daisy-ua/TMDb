package com.example.tmdb.data.repository

import android.app.Application
import androidx.room.withTransaction
import com.example.tmdb.data.local.LocalDatabase
import com.example.tmdb.data.local.entities.toMovieDB
import com.example.tmdb.data.local.entities.toMovieDomain
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.models.MovieDTOMapper
import com.example.tmdb.data.remote.repositories.MovieHelper
import com.example.tmdb.utils.Resource
import com.example.tmdb.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

class CacheRepository(context: Application) {
    private val movieRepository = MovieHelper(NetworkManager.movieServices, MovieDTOMapper())
    private val db = LocalDatabase.getInstance(context)
    private val movieDao = db.movieDao()

    suspend fun getMovieDetails(id: Int): Movie =
        movieRepository.getDetails(id)

    fun getPopularMovies() : Flow<Resource<List<Movie>>> =
        networkBoundResource(
            query = { movieDao.getPopular().toMovieDomain() },
            fetch = { movieRepository.getPopular() },
            saveFetchResult = { movies ->
                db.withTransaction {
                    movieDao.deleteAllMovies()
                    movieDao.insertMovies(movies.toMovieDB())
                }
            }
        )

    suspend fun getSimilarMovies(id: Int): List<Movie> =
        movieRepository.getSimilar(id)

    suspend fun searchMovies(query: String) : List<Movie> =
        movieRepository.search(query)

    suspend fun discoverMovies(genre_ids: String) : List<Movie> =
        movieRepository.discover(genre_ids)

//    suspend fun getGenres(): List<Genre> = genreRepository.getGenres()
}