package com.example.tmdb.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.repositories.moviepaginatedpreview.MoviePaginatedPreviewRepository
import com.tmdb.repository.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviePaginatedPreviewRepository: MoviePaginatedPreviewRepository,
) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<Response<MoviePaginated>>(Response.Loading())
    val nowPlayingMovies: StateFlow<Response<MoviePaginated>> get() = _nowPlayingMovies

    private val _trendingMovies = MutableStateFlow<Response<MoviePaginated>>(Response.Loading())
    val trendingMovies: StateFlow<Response<MoviePaginated>> get() = _trendingMovies

    private val _topRatedMovies = MutableStateFlow<Response<MoviePaginated>>(Response.Loading())
    val topRatedMovies: StateFlow<Response<MoviePaginated>> get() = _topRatedMovies

    init {

        fetchTrendingMovies()

        fetchNowPlayingMovies()

        fetchTopRatedMovies()
    }

    private fun fetchTrendingMovies() = viewModelScope.launch {
        moviePaginatedPreviewRepository.fetchTrendingMoviesPreview().collect {
            _trendingMovies.value = it
        }
    }

    private fun fetchNowPlayingMovies() = viewModelScope.launch {
        moviePaginatedPreviewRepository.fetchNowPlayingMoviesPreview().collect {
            _nowPlayingMovies.value = it
        }
    }

    private fun fetchTopRatedMovies() = viewModelScope.launch {
        moviePaginatedPreviewRepository.fetchTopRatedMoviesPreview().collect {
            _topRatedMovies.value = it
        }
    }
}