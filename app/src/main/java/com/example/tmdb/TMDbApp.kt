package com.example.tmdb

import android.app.Application
import com.example.tmdb.data.repository.CacheRepository

class TMDbApp : Application() {
    companion object {
        lateinit var repository: CacheRepository
    }

    override fun onCreate() {
        super.onCreate()
        repository = CacheRepository(this)
    }
}