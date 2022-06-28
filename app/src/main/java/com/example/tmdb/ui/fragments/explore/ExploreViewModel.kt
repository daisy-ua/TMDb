package com.example.tmdb.ui.fragments.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.constants.FilterKeys
import com.tmdb.models.Genre
import com.tmdb.repository.repositories.discover_repository.DiscoverRepository
import com.tmdb.repository.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
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

    init {
        fetchGenres()
    }

    private fun fetchGenres() = viewModelScope.launch {
        discoverRepository.fetchMovieGenres().collect { response ->
            _genres.postValue(response)
        }
    }
}

