package com.example.tmdb.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.room.withTransaction
import com.example.tmdb.data.local.LocalDatabase
import com.example.tmdb.data.local.entities.MovieDB
import com.example.tmdb.data.local.entities.toMovieDB
import com.example.tmdb.data.local.entities.toMovieDomain
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.models.MovieDTOMapper
import com.example.tmdb.data.remote.repositories.MovieHelper
import com.example.tmdb.utils.Resource
import com.example.tmdb.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CacheRepository(context: Application) {
    private val movieRepository = MovieHelper(NetworkManager.movieServices, MovieDTOMapper())
    private val db = LocalDatabase.getInstance(context)
    private val movieDao = db.movieDao()

    suspend fun getMovieDetails(id: Int, api_key: String): Movie =
        movieRepository.getDetails(id, api_key)

    fun getPopularMovies(api_key: String) : Flow<Resource<List<Movie>>> =
        networkBoundResource(
            query = { movieDao.getPopular().toMovieDomain() },
            fetch = { movieRepository.getPopular(api_key) },
            saveFetchResult = { movies ->
                db.withTransaction {
                    movieDao.deleteAllMovies()
                    movieDao.insertMovies(movies.toMovieDB())
                }
            }
        )

    suspend fun getSimilarMovies(id: Int, api_key: String): List<Movie> =
        movieRepository.getSimilar(id, api_key)

    suspend fun searchMovies(query: String, api_key: String) : List<Movie> =
        movieRepository.search(query, api_key)

    suspend fun discoverMovies(genre_ids: String, api_key: String) : List<Movie> =
        movieRepository.discover(genre_ids, api_key)

//    suspend fun getGenres(api_key: String): List<Genre> = genreRepository.getGenres(api_key)
}