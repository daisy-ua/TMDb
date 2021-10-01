package com.example.tmdb.data.repository

import android.app.Application
import androidx.room.withTransaction
import com.example.tmdb.data.local.database.LocalDatabase
import com.example.tmdb.data.local.repository.LocalMovieHelper
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.repositories.RemoteMovieHelper
import com.example.tmdb.utils.network.Resource
import com.example.tmdb.utils.networkBoundResource
import kotlinx.coroutines.flow.*

class DiscoverRepository(context: Application) {
    private val db = LocalDatabase.getInstance(context)
    private val remoteHelper = RemoteMovieHelper(NetworkManager.movieServices)
    private val localHelper = LocalMovieHelper(db.movieDao())

    suspend fun getMovieDetails(id: Int): Flow<Resource<Movie>> =
        flow { emit(Resource.Success(remoteHelper.getDetails(id))) }

    suspend fun clear() = localHelper.deleteAll()

    fun getPopularMovies(page: Int) : Flow<Resource<List<Movie>>> =
        flow { emit (Resource.Success(remoteHelper.getAll(page))) }
//        networkBoundResource(
//            query = { localHelper.getAll() },
//            fetch = { remoteHelper.getAll(page) },
//            saveFetchResult = { movies ->
//                db.withTransaction {
//                    localHelper.deleteAll()
//                    localHelper.insert(movies)
//                }
//            }
//        )

    suspend fun getSimilarMovies(id: Int): Flow<Resource<List<Movie>>> =
        flow { emit(Resource.Success(remoteHelper.getSimilar(id))) }

    suspend fun searchMovies(query: String) : List<Movie> =
        remoteHelper.search(query)

    suspend fun discoverMovies(page: Int, genre_ids: String) : Flow<Resource<List<Movie>>> =
        flow { emit(Resource.Success(remoteHelper.discover(page, genre_ids))) }
}