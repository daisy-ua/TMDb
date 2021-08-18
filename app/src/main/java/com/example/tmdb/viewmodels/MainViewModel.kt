package com.example.tmdb.viewmodels

import androidx.lifecycle.*
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.CacheRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CacheRepository) : ViewModel() {
    val popularMoviesResponse = MutableLiveData<Resource<List<Movie>>>()

    init {
        setPopularMovies()
    }

    private fun setPopularMovies() = viewModelScope.launch {
        repository.getPopularMovies().collect() { popularMoviesResponse.postValue(it) }
    }

    fun getMovieDetails(id: Int) = viewModelScope.launch {
        repository.getMovieDetails(id)
    }

    fun getSimilarMovies(id: Int) = viewModelScope.launch {
        repository.getSimilarMovies(id)
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        repository.searchMovies(query)
    }

    fun discoverMovies(genre_ids: String) = viewModelScope.launch {
        repository.discoverMovies(genre_ids)
    }

//    fun getGenres() = viewModelScope.launch {
//        repository.getGenres()
//    }
}