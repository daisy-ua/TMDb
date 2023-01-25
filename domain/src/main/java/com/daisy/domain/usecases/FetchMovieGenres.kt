package com.daisy.domain.usecases

import com.daisy.domain.repository.DiscoverRepository
import javax.inject.Inject

class FetchMovieGenres @Inject constructor(
    private val repository: DiscoverRepository,
) {
    suspend operator fun invoke() = repository.fetchMovieGenres()
}