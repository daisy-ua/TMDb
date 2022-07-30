package com.example.tmdb.ui.fragments.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tmdb.models.Video
import com.tmdb.models.movies.MovieDetails
import com.tmdb.repository.repositories.movie_details_repository.MovieDetailsRepository
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
) : ViewModel() {
    private val _movieDetails = MutableLiveData<Response<MovieDetails>?>()
    val movieDetails: LiveData<Response<MovieDetails>?> get() = _movieDetails

    private val _videos = MutableStateFlow<Response<List<Video>>>(Response.Loading())
    val videos: StateFlow<Response<List<Video>>> = _videos

    fun fetchMovieDetails(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.fetchMovieDetails(movieId).collect { response ->
            _movieDetails.postValue(response)
        }
    }

    fun fetchMovieVideos(movieId: Int) = viewModelScope.launch {
        movieDetailsRepository.fetchMovieVideos(movieId).collect { response ->
            _videos.value = response
        }
    }
}
