package com.example.tmdb.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.repository.GenreRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GenreViewModel(private val repository: GenreRepository) : ViewModel() {
    val genres = MutableLiveData<List<Genre>>()

    fun setGenreNames(vararg ids: Int) = viewModelScope.launch {
        repository.getGenresById(*ids).collect { list ->
            genres.value = list
        }
    }
}