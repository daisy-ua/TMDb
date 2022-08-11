package com.example.tmdb.ui.fragments.seemore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.constants.HomeCategory
import com.tmdb.models.movies.Movie
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val moviePaginatedRepository: MoviePaginatedRepository,
) : ViewModel() {
    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> get() = _movies

    fun fetchMovies(category: HomeCategory) {
        viewModelScope.launch {
            val fetchedData = when (category) {
                HomeCategory.NOW_PLAYING -> fetchNowPlayingMovies()

                HomeCategory.TRENDING -> fetchTrendingMovies()

                HomeCategory.TOP_RATED -> fetchTopRatedMovies()
            }

            launch {
                fetchedData.collect { movies ->
                    _movies.value = movies
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