package com.example.tmdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.DiscoverRepository
import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val discoverRepository: DiscoverRepository) : ViewModel() {
    private val _movieResponse = MutableLiveData<Resource<Movie>>()
    val movieResponse: LiveData<Resource<Movie>> get() = _movieResponse

    val similarMovies = MutableLiveData<Resource<List<Movie>>>()

    suspend fun setSimilarMovies(id: Int) = withContext(Dispatchers.IO) {
        discoverRepository.getSimilarMovies(id)
            .collect { similarMovies.postValue(it) }
    }

    suspend fun setMovieDetails(id: Int) = withContext(Dispatchers.IO) {
        discoverRepository.getMovieDetails(id)
            .collect{ response -> _movieResponse.postValue(response) }
    }
}