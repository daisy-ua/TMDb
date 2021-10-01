package com.example.tmdb.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.repository.DiscoverRepository
import com.example.tmdb.data.repository.GenreRepository
import com.example.tmdb.utils.network.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val discoverRepository: DiscoverRepository,
    private val genreRepository: GenreRepository
) : ViewModel(), PagingAdapter<Movie> {
    val popularMoviesResponse = MutableLiveData<Resource<List<Movie>>>()

    override val result: MutableLiveData<Resource<List<Movie>>>
        get() = popularMoviesResponse

    override var currentPage: Int = 1

    init {
        loadNextPage()
    }

    fun loadNextPage() = viewModelScope.launch {
        invokeNextPageLoading(discoverRepository.getPopularMovies(currentPage))
    }
}