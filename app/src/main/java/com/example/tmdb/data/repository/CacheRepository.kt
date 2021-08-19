package com.example.tmdb.data.repository

import android.app.Application
import androidx.room.withTransaction
import com.example.tmdb.data.local.LocalDatabase
import com.example.tmdb.data.local.repository.LocalMovieHelper
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.repositories.RemoteMovieHelper
import com.example.tmdb.utils.Resource
import com.example.tmdb.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

class CacheRepository(context: Application) {
    private val db = LocalDatabase.getInstance(context)
    private val remoteHelper = RemoteMovieHelper(NetworkManager.movieServices)
    private val localHelper = LocalMovieHelper(db.movieDao())

    suspend fun getMovieDetails(id: Int): Movie =
        remoteHelper.getDetails(id)

    suspend fun clear() = localHelper.deleteAll()

    fun getPopularMovies() : Flow<Resource<List<Movie>>> =
        networkBoundResource(
            query = { localHelper.getPopular() },
            fetch = { remoteHelper.getPopular() },
            saveFetchResult = { movies ->
                db.withTransaction {
                    localHelper.deleteAll()
                    localHelper.insert(movies)
                }
            }
        )

    suspend fun getSimilarMovies(id: Int): List<Movie> =
        remoteHelper.getSimilar(id)

    suspend fun searchMovies(query: String) : List<Movie> =
        remoteHelper.search(query)

    suspend fun discoverMovies(genre_ids: String) : List<Movie> =
        remoteHelper.discover(genre_ids)

//    suspend fun getGenres(): List<Genre> = genreRepository.getGenres()
}