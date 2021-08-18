package com.example.tmdb.viewmodels

import androidx.lifecycle.*
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.CacheRepository
import com.example.tmdb.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CacheRepository) : ViewModel() {
    val popularMoviesResponse = MutableLiveData<Resource<List<Movie>>>()

    fun setPopularMovies(api_key: String) = viewModelScope.launch {
        repository.getPopularMovies(api_key).collect() { popularMoviesResponse.postValue(it) }
    }

    fun getMovieDetails(id: Int, api_key: String) = viewModelScope.launch {
        repository.getMovieDetails(id, api_key)
    }

    fun getSimilarMovies(id: Int, api_key: String) = viewModelScope.launch {
        repository.getSimilarMovies(id, api_key)
    }

    fun searchMovies(query: String, api_key: String) = viewModelScope.launch {
        repository.searchMovies(query, api_key)
    }

    fun discoverMovies(genre_ids: String, api_key: String) = viewModelScope.launch {
        repository.discoverMovies(genre_ids, api_key)
    }

//    fun getGenres(api_key: String) = viewModelScope.launch {
//        repository.getGenres(api_key)
//    }
}