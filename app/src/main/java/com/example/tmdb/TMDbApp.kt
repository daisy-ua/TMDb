package com.example.tmdb

import android.app.Application
import com.example.tmdb.data.remote.NetworkManager
import com.example.tmdb.data.remote.models.MovieDTOMapper
import com.example.tmdb.data.remote.repositories.GenreRepository
import com.example.tmdb.data.remote.repositories.MovieRepository
import com.example.tmdb.data.repository.Repository

class TMDbApp : Application() {
    companion object {
        lateinit var repository: Repository
    }

    override fun onCreate() {
        super.onCreate()
        val movieRepository = MovieRepository(NetworkManager.movieServices, MovieDTOMapper())
        val genreRepository = GenreRepository(NetworkManager.genreServices)
        repository = Repository(movieRepository, genreRepository)
    }
}