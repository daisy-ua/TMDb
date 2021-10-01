package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.application.TMDbApp

class ExploreViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ExploreViewModel(
            TMDbApp.discoverRepository,
            TMDbApp.genreRepository
        ) as T
}