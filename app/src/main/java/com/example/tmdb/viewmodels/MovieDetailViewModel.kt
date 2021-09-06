package com.example.tmdb.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.DiscoverRepository
import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val discoverRepository: DiscoverRepository) : ViewModel() {
    val similarMovies = MutableLiveData<Resource<List<Movie>>>()

    suspend fun setSimilarMovies(id: Int) = withContext(Dispatchers.IO) {
        discoverRepository.getSimilarMovies(id).collect { similarMovies.postValue(it) }
    }
}