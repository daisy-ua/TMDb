package com.example.tmdb.utils

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.tmdb.viewmodels.MainViewModel
import com.example.tmdb.viewmodels.MainViewModelFactory

fun getMainViewModel(owner: ViewModelStoreOwner) : MainViewModel {
    val viewModelProviderFactory = MainViewModelFactory()
    return ViewModelProvider(owner, viewModelProviderFactory)[MainViewModel::class.java]
}