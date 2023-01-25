package com.daisy.domain.usecases

import com.daisy.domain.repository.MoviePaginatedPreviewRepository
import javax.inject.Inject

class FetchNowPlayingMoviesPreview @Inject constructor(
    private val repository: MoviePaginatedPreviewRepository,
) {
    suspend operator fun invoke() = repository.fetchNowPlayingMoviesPreview()
}