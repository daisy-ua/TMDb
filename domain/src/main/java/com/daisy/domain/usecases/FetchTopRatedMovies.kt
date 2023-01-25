package com.daisy.domain.usecases

import com.daisy.domain.repository.MoviePaginatedRepository
import javax.inject.Inject

class FetchTopRatedMovies @Inject constructor(
    private val repository: MoviePaginatedRepository,
) {
    suspend operator fun invoke() = repository.fetchTopRatedMovies()
}