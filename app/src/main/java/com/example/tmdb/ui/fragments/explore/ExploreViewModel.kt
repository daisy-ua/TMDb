package com.example.tmdb.ui.fragments.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.constants.FilterKeys
import com.example.tmdb.utils.converters.QueryFormatFiltersConverter
import com.tmdb.models.Genre
import com.tmdb.models.movies.Movie
import com.tmdb.repository.repositories.discover_repository.DiscoverRepository
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import com.tmdb.repository.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val movieRepository: MoviePaginatedRepository,
    private val discoverRepository: DiscoverRepository,
) : ViewModel() {
    private val _genres = MutableLiveData<Response<List<Genre>>?>()
    val genres: LiveData<Response<List<Genre>>?> get() = _genres

    private val _filters = MutableLiveData<MutableMap<String, Any>>(mutableMapOf(
        FilterKeys.SORT_BY to 0
    ))

    val filters: LiveData<MutableMap<String, Any>> get() = _filters

    fun setFilters(sortBy: Int, withGenres: List<Int>) {
        _filters.value = filters.value?.also { _map ->
            _map[FilterKeys.SORT_BY] = sortBy
            _map[FilterKeys.WITH_GENRES] = withGenres
        }
    }

    private val queryFormatConverter = QueryFormatFiltersConverter

    @Suppress("UNCHECKED_CAST")
    private val discoverOptions: Map<String, String>
        get() = mutableMapOf<String, String>().apply {
            filters.value?.let { filters ->
                (filters[FilterKeys.SORT_BY] as Int).let { sortBy ->
                    if (sortBy != -1) {
                        put(FilterKeys.SORT_BY, queryFormatConverter.getSortBy(sortBy))
                    }
                }

                (filters[FilterKeys.WITH_GENRES] as List<Int>?)?.let { withGenres ->
                    put(FilterKeys.WITH_GENRES, queryFormatConverter.getWithGenres(withGenres))
                }
            }
        }

    init {
        fetchGenres()
    }

    suspend fun fetchMovieDiscoverResult(): Flow<PagingData<Movie>> =
        movieRepository
            .fetchMovieDiscoverResult(discoverOptions)
            .cachedIn(viewModelScope)

    suspend fun fetchMovieSearchResult(query: String) =
        movieRepository
            .fetchMovieSearchResult(query)
            .cachedIn(viewModelScope)

    private fun fetchGenres() = viewModelScope.launch {
        discoverRepository.fetchMovieGenres().collect { response ->
            _genres.postValue(response)
        }
    }
}