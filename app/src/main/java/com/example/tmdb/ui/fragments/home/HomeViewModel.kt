package com.example.tmdb.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.constants.Response
import com.daisy.domain.models.movies.MoviePaginated
import com.daisy.domain.usecases.FetchNowPlayingMoviesPreview
import com.daisy.domain.usecases.FetchTopRatedMoviesPreview
import com.daisy.domain.usecases.FetchTrendingMoviesPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchTopRatedMoviesPreview: FetchTopRatedMoviesPreview,
    private val fetchNowPlayingMoviesPreview: FetchNowPlayingMoviesPreview,
    private val fetchTrendingMoviesPreview: FetchTrendingMoviesPreview,
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
        fetchTrendingMoviesPreview.invoke().collect {
            _trendingMovies.value = it
        }
    }

    private fun fetchNowPlayingMovies() = viewModelScope.launch {
        fetchNowPlayingMoviesPreview.invoke().collect {
            _nowPlayingMovies.value = it
        }
    }

    private fun fetchTopRatedMovies() = viewModelScope.launch {
        fetchTopRatedMoviesPreview.invoke().collect {
            _topRatedMovies.value = it
        }
    }
}