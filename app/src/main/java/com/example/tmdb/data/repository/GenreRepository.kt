package com.example.tmdb.data.repository

import android.app.Application
import androidx.room.withTransaction
import com.example.tmdb.data.local.database.LocalDatabase
import com.example.tmdb.data.local.repository.LocalGenreHelper
import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.repositories.RemoteGenreHelper
import com.example.tmdb.utils.network.Resource
import com.example.tmdb.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

class GenreRepository(context: Application) {
    private val db = LocalDatabase.getInstance(context)
    private val remoteHelper = RemoteGenreHelper(NetworkManager.genreServices)
    private val localHelper = LocalGenreHelper(db.genreDao())

    fun getGenres() : Flow<Resource<List<Genre>>> = networkBoundResource(
        query = { localHelper.getAll() },
        fetch = { remoteHelper.getAll() },
        saveFetchResult = { genres ->
            db.withTransaction {
                localHelper.deleteAll()
                localHelper.insert(genres)
            }
        }
    )

    fun getGenresById(vararg ids: Int) : Flow<List<Genre>> = localHelper.getById(*ids)
}