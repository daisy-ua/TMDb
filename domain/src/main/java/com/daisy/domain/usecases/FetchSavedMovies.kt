package com.daisy.domain.usecases

import com.daisy.domain.repository.SavedRepository
import javax.inject.Inject

class FetchSavedMovies @Inject constructor(
    private val repository: SavedRepository,
) {
    suspend operator fun invoke() = repository.fetchSavedMovies()
}