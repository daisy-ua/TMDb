package com.daisy.domain.usecases

import com.daisy.domain.repository.MoviePaginatedRepository
import javax.inject.Inject

class FetchMovieSearchResult @Inject constructor(
    private val repository: MoviePaginatedRepository,
) {
    suspend operator fun invoke(query: String) = repository.fetchMovieSearchResult(query)
}