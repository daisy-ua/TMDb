package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.DiscoverRepository
import com.example.tmdb.data.repository.GenreRepository
import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val discoverRepository: DiscoverRepository,
    private val genreRepository: GenreRepository
) : ViewModel(), PagingAdapter<Movie> {
    val moviesByGenre = MutableLiveData<Resource<List<Movie>>>()
    val genresResponse = MutableLiveData<Resource<List<Genre>>>()

    override val result: MutableLiveData<Resource<List<Movie>>>
        get() = moviesByGenre

    override var currentPage: Int = 1

    var genreIds: String = ""

    init { setGenres() }

    fun loadNextPage() = viewModelScope.launch {
        invokeNextPageLoading(discoverRepository.discoverMovies(currentPage, genreIds))
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        discoverRepository.searchMovies(query)
    }

    // TODO: replace with ViewPager or multi filter
    fun resetMoviesByGenre() {
        moviesByGenre.value = Resource.Success(emptyList())
    }

    private fun setGenres() = viewModelScope.launch {
        genreRepository.getGenres().collect { genresResponse.postValue(it) }
    }
}