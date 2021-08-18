package com.example.tmdb

import android.app.Application
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.models.MovieDTOMapper
import com.example.tmdb.data.remote.repositories.GenreRepository
import com.example.tmdb.data.remote.repositories.MovieHelper
import com.example.tmdb.data.repository.CacheRepository

class TMDbApp : Application() {
    companion object {
        lateinit var repository: CacheRepository
    }

    override fun onCreate() {
        super.onCreate()
        val movieRepository = MovieHelper(NetworkManager.movieServices, MovieDTOMapper())
        val genreRepository = GenreRepository(NetworkManager.genreServices)
        repository = CacheRepository(this)
    }
}