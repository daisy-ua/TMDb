package com.daisy.domain.usecases

import com.daisy.domain.repository.MoviePaginatedRepository
import javax.inject.Inject

class FetchMovieDiscoverResult @Inject constructor(
    private val repository: MoviePaginatedRepository,
) {
    suspend operator fun invoke(
        options: Map<String, String> = mapOf(),
        includeAdult: Boolean = false,
    ) = repository.fetchMovieDiscoverResult(options, includeAdult)
}