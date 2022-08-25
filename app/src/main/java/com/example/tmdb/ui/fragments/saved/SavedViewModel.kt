package com.example.tmdb.ui.fragments.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.insertHeaderItem
import com.example.tmdb.ui.utils.eventmanager.events.BaseEvents
import com.tmdb.models.movies.Movie
import com.tmdb.repository.repositories.savedrepository.SavedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val savedRepository: SavedRepository,
) : ViewModel() {
    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> get() = _movies

    init {
        viewModelScope.launch {
            fetchSavedMovies().collect {
                _movies.value = it
            }
        }
    }

    private suspend fun fetchSavedMovies() = savedRepository.fetchSavedMovies()
        .cachedIn(viewModelScope)

    fun applyEvents(
        paging: PagingData<Movie>,
        event: BaseEvents,
    ): PagingData<Movie> {
        return when (event) {
            is BaseEvents.Remove -> {
                paging
                    .filter { movie ->
                        event.id != movie.id
                    }
            }

            is BaseEvents.InsertItemHeader -> {
                paging.insertHeaderItem(
                    item = event.item as Movie
                )
            }
        }
    }
}

