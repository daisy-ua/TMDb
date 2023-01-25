package com.daisy.domain.usecases

import com.daisy.domain.repository.SavedRepository
import javax.inject.Inject

class DeleteMovieSaved @Inject constructor(
    private val repository: SavedRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.deleteSaved(movieId)
}