package com.example.tmdb.viewmodels

import androidx.lifecycle.*
import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.DiscoverRepository
import com.example.tmdb.data.repository.GenreRepository
import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val discoverRepository: DiscoverRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {
    val popularMoviesResponse = MutableLiveData<Resource<List<Movie>>>()
    val moviesByGenre = MutableLiveData<Resource<List<Movie>>>()
    val genresResponse = MutableLiveData<Resource<List<Genre>>>()

    init {
//        viewModelScope.launch{ discoverRepository.clear() }
        setPopularMovies()
        setGenres()
    }

    private fun setPopularMovies() = viewModelScope.launch {
        discoverRepository.getPopularMovies().collect() { popularMoviesResponse.postValue(it) }
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        discoverRepository.searchMovies(query)
    }

    fun discoverMovies(genre_ids: String) = viewModelScope.launch {
        discoverRepository.discoverMovies(genre_ids).collect { moviesByGenre.postValue(it) }
    }

    fun setGenres() = viewModelScope.launch {
        genreRepository.getGenres().collect() { genresResponse.postValue(it) }
    }
}