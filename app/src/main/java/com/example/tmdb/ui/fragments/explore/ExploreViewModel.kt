package com.example.tmdb.ui.fragments.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tmdb.constants.AppConstants.DEFAULT_QUERY
import com.example.tmdb.ui.utils.uistate.UiAction
import com.example.tmdb.ui.utils.uistate.UiState
import com.example.tmdb.utils.getFiltersFormatted
import com.tmdb.models.Genre
import com.tmdb.models.movies.Movie
import com.tmdb.repository.repositories.discover_repository.DiscoverRepository
import com.tmdb.repository.repositories.movie_paginated_repository.MoviePaginatedRepository
import com.tmdb.repository.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val movieRepository: MoviePaginatedRepository,
    private val discoverRepository: DiscoverRepository,
) : ViewModel() {
    private val _genres = MutableLiveData<Response<List<Genre>>?>()
    val genres: LiveData<Response<List<Genre>>?> get() = _genres

    val state: StateFlow<UiState>

    var pagingDataFlow: Flow<PagingData<Movie>>

    val accept: (UiAction) -> Unit

    var filters: Map<String, *>? = null
        private set

    private val discoverOptions: Map<String, String>
        get() = getFiltersFormatted(state.value.filters)

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()

        val searches = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search(query = DEFAULT_QUERY)) }

        val queriesScrolled = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(UiAction.Scroll(currentQuery = DEFAULT_QUERY)) }

        pagingDataFlow = searches
            .flatMapLatest {
                if (it.query.isNotEmpty()) fetchMovieSearchResult(it.query)
                else fetchMovieDiscoverResult()
            }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            UiState(
                query = search.query,
                filters = search.filters,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery,
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = UiState(
                    filters = filters
                )
            )

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }

        fetchGenres()
    }

    fun saveCurrentDataState() {
        filters = state.value.filters
    }

    private suspend fun fetchMovieDiscoverResult(): Flow<PagingData<Movie>> =
        movieRepository
            .fetchMovieDiscoverResult(discoverOptions)

    private suspend fun fetchMovieSearchResult(query: String) =
        movieRepository
            .fetchMovieSearchResult(query)

    private fun fetchGenres() = viewModelScope.launch {
        discoverRepository.fetchMovieGenres().collect { response ->
            _genres.postValue(response)
        }
    }
}