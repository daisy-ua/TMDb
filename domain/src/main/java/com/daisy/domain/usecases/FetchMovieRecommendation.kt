package com.daisy.domain.usecases

import com.daisy.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class FetchMovieRecommendation @Inject constructor(
    private val repository: MovieDetailsRepository,
) {
    suspend operator fun invoke(movieId: Int) = repository.fetchMovieRecommendation(movieId)
}