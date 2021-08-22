package com.example.tmdb.application

import android.app.Application
import com.example.tmdb.data.repository.DiscoverRepository
import com.example.tmdb.data.repository.GenreRepository

class TMDbApp : Application() {
    companion object {
        lateinit var discoverRepository: DiscoverRepository
        lateinit var genreRepository: GenreRepository
    }

    override fun onCreate() {
        super.onCreate()
        discoverRepository = DiscoverRepository(this)
        genreRepository = GenreRepository(this)
    }
}