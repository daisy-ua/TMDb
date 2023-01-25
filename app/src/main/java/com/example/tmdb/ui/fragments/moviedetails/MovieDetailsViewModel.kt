package com.example.tmdb.ui.fragments.moviedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daisy.constants.Response
import com.daisy.domain.models.Video
import com.daisy.domain.models.movies.MovieDetails
import com.daisy.domain.models.movies.MoviePaginated
import com.daisy.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val fetchMovieDetails: FetchMovieDetails,
    private val fetchMovieVideos: FetchMovieVideos,
    private val fetchMovieRecommendation: FetchMovieRecommendation,
    private val isMovieSaved: IsMovieSaved,
    private val insertMovieSaved: InsertMovieSaved,
    private val deleteMovieSaved: DeleteMovieSaved,
) : ViewModel() {
    private val _movieDetails =
        MutableStateFlow<Response<MovieDetails>?>(Response.Loading())
    val movieDetails: StateFlow<Response<MovieDetails>?> get() = _movieDetails

    private val _movieRecommendations =
        MutableStateFlow<Response<MoviePaginated>?>(Response.Loading())

    val movieRecommendations: StateFlow<Response<MoviePaginated>?>
        get() = _movieRecommendations

    private val _videos =
        MutableStateFlow<Response<List<Video>>>(Response.Loading())
    val videos: StateFlow<Response<List<Video>>> = _videos

    private val _isSaved =
        MutableStateFlow<Response<Boolean>>(Response.Loading())
    val isSaved: StateFlow<Response<Boolean>> = _isSaved

    fun fetchMovieDetails(movieId: Int) = viewModelScope.launch {
        fetchMovieDetails.invoke(movieId).collect { response ->
            _movieDetails.value = response
        }
    }

    fun fetchMovieRecommendations(movieId: Int) = viewModelScope.launch {
        fetchMovieRecommendation.invoke(movieId).collect { response ->
            _movieRecommendations.value = response
        }
    }

    fun fetchMovieVideos(movieId: Int) = viewModelScope.launch {
        fetchMovieVideos.invoke(movieId).collect { response ->
            _videos.value = response
        }
    }

    fun isMovieSaved(movieId: Int) = viewModelScope.launch {
        isMovieSaved.invoke(movieId).collect { response ->
            _isSaved.value = response
        }
    }

    fun updateIsMovieSaved(movieId: Int) = viewModelScope.launch {
        try {
            if (isSaved.value.data == true) {
                deleteMovieSaved.invoke(movieId)
            } else {
                insertMovieSaved.invoke(movieId)
            }
            _isSaved.value = Response.Success(!isSaved.value.data!!)
        } catch (ex: Exception) {
            Log.d("daisy", ex.message.toString())
        }
    }
}
