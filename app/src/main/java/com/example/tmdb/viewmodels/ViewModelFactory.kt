package com.example.tmdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

fun getMainViewModel(owner: ViewModelStoreOwner) : MainViewModel {
    val viewModelProviderFactory = MainViewModelFactory()
    return ViewModelProvider(owner, viewModelProviderFactory)[MainViewModel::class.java]
}

fun getMovieDetailViewModel(owner: ViewModelStoreOwner) : MovieDetailViewModel {
    val viewModelProviderFactory = MovieDetailViewModelFactory()
    return ViewModelProvider(owner, viewModelProviderFactory)[MovieDetailViewModel::class.java]
}

inline fun <reified T: ViewModel> getViewModel(owner: ViewModelStoreOwner, providerFactory: ViewModelProvider.Factory) =
    ViewModelProvider(owner, providerFactory)[T::class.java]