package com.example.tmdb.ui.fragments.movie_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.models.Video
import com.tmdb.models.movies.MovieDetails
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepository
import com.tmdb.repository.repositories.savedrepository.SavedRepository
import com.tmdb.repository.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val savedRepository: SavedRepository
) : ViewModel() {
    private val _movieDetails = MutableStateFlow<Response<MovieDetails>?>(Response.Loading())
    val movieDetails: StateFlow<Response<MovieDetails>?> get() = _movieDetails

    private val _movieRecommendations =
        MutableStateFlow<Response<MoviePaginated>?>(Response.Loading())

    val movieRecommendations: StateFlow<Response<MoviePaginated>?>
        get() = _movieRecommendations

    private val _videos = MutableStateFlow<Response<List<Video>>>(Response.Loading())
    val videos: StateFlow<Response<List<Video>>> = _videos

    private val _isSaved = MutableStateFlow<Response<Boolean>>(Response.Loading())
    val isSaved: StateFlow<Response<Boolean>> = _isSaved

    fun fetchMovieDetails(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.fetchMovieDetails(movieId).collect { response ->
            _movieDetails.value = response
        }
    }

    fun fetchMovieRecommendations(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.fetchMovieRecommendation(movieId).collect { response ->
            _movieRecommendations.value = response
        }
    }

    fun fetchMovieVideos(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.fetchMovieVideos(movieId).collect { response ->
            _videos.value = response
        }
    }

    fun isMovieSaved(movieId: Int) = viewModelScope.launch {
        savedRepository.isSaved(movieId).collect { response ->
            _isSaved.value = response
        }
    }

    fun updateIsMovieSaved(movieId: Int) = viewModelScope.launch {
        try {
            if (isSaved.value.data == true) {
                savedRepository.deleteSaved(movieId)
            } else {
                savedRepository.insertSaved(movieId)
            }
            _isSaved.value = Response.Success(!isSaved.value.data!!)
        } catch (ex: Exception) {
            Log.d("daisy", ex.message.toString())
        }
    }
}
