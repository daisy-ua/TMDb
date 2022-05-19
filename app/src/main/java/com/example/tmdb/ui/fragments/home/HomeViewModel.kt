package com.example.tmdb.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import com.tmdb.repository.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePaginatedRepository: MoviePaginatedRepository,
) : ViewModel() {
    private val _popularMovies = MutableLiveData<Response<MoviePaginated>?>()
    val popularMovies: LiveData<Response<MoviePaginated>?> get() = _popularMovies

    private val _nowPlayingMovies = MutableLiveData<Response<MoviePaginated>?>()
    val nowPlayingMovies: LiveData<Response<MoviePaginated>?> get() = _nowPlayingMovies

    private val _topRatedMovies = MutableLiveData<Response<MoviePaginated>?>()
    val topRatedMovies: LiveData<Response<MoviePaginated>?> get() = _topRatedMovies

    init {
        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
    }

    private fun fetchPopularMovies() = viewModelScope.launch {
        moviePaginatedRepository.fetchPopularMovies().collect { response ->
            _popularMovies.postValue(response)
        }
    }

    private fun fetchNowPlayingMovies() = viewModelScope.launch {
        moviePaginatedRepository.fetchNowPlayingMovies().collect { response ->
            _nowPlayingMovies.postValue(response)
        }
    }

    private fun fetchTopRatedMovies() = viewModelScope.launch {
        moviePaginatedRepository.fetchTopRatedMovies().collect { response ->
            _topRatedMovies.postValue(response)
        }
    }
}
