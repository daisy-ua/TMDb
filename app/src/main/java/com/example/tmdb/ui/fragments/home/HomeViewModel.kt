package com.example.tmdb.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tmdb.models.movies.Movie
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePaginatedRepository: MoviePaginatedRepository,
) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val nowPlayingMovies: StateFlow<PagingData<Movie>> get() = _nowPlayingMovies

    private val _trendingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val trendingMovies: StateFlow<PagingData<Movie>> get() = _trendingMovies

    private val _topRatedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val topRatedMovies: StateFlow<PagingData<Movie>> get() = _topRatedMovies

    init {
        viewModelScope.launch {

            launch {
                fetchNowPlayingMovies().collect { movies ->
                    _nowPlayingMovies.value = movies
                }
            }

            launch {
                fetchTrendingMovies().collect { movies ->
                    _trendingMovies.value = movies
                }
            }

            launch {
                fetchTopRatedMovies().collect { movies ->
                    _topRatedMovies.value = movies
                }
            }
        }
    }

    private suspend fun fetchTrendingMovies() = moviePaginatedRepository.fetchTrendingMovies()
        .cachedIn(viewModelScope)

    private suspend fun fetchNowPlayingMovies() = moviePaginatedRepository.fetchNowPlayingMovies()
        .cachedIn(viewModelScope)

    private suspend fun fetchTopRatedMovies() = moviePaginatedRepository.fetchTopRatedMovies()
        .cachedIn(viewModelScope)
}