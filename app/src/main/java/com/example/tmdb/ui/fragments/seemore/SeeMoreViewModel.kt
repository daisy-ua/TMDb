package com.example.tmdb.ui.fragments.seemore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.daisy.domain.models.movies.Movie
import com.daisy.domain.usecases.FetchNowPlayingMovies
import com.daisy.domain.usecases.FetchTopRatedMovies
import com.daisy.domain.usecases.FetchTrendingMovies
import com.example.tmdb.constants.HomeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val fetchTopRatedMovies: FetchTopRatedMovies,
    private val fetchNowPlayingMovies: FetchNowPlayingMovies,
    private val fetchTrendingMovies: FetchTrendingMovies,
) : ViewModel() {
    private val _movies =
        MutableStateFlow<PagingData<Movie>>(PagingData.empty())
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

    private suspend fun fetchTrendingMovies() = fetchTrendingMovies.invoke()
        .cachedIn(viewModelScope)

    private suspend fun fetchNowPlayingMovies() = fetchNowPlayingMovies.invoke()
        .cachedIn(viewModelScope)

    private suspend fun fetchTopRatedMovies() = fetchTopRatedMovies.invoke()
        .cachedIn(viewModelScope)
}