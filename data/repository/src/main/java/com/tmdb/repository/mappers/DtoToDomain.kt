package com.tmdb.repository.mappers

import com.tmdb.models.Country
import com.tmdb.models.Genre
import com.tmdb.models.ProductionStatus
import com.tmdb.models.SpokenLanguage
import com.tmdb.models.movies.Movie
import com.tmdb.models.movies.MovieDetails
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.network.models.CountryDto
import com.tmdb.network.models.SpokenLanguageDto
import com.tmdb.network.models.genre.GenreDto
import com.tmdb.network.models.genre.GenreListDto
import com.tmdb.network.models.movie.MovieDetailsDto
import com.tmdb.network.models.movie.MovieDto
import com.tmdb.network.models.movie.MoviePaginatedDto

internal fun MovieDto.toDomain(): Movie {
    return Movie(
        posterPath = posterPath,
        adult = adult,
        overview = overview,
        releaseDate = releaseDate ?: "",
        genreIds = genreIds ?: listOf(),
        id = id,
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        title = title,
        backdropPath = backdropPath,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        video = video
    )
}

internal fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        adult = adult,
        backdropPath = backdropPath,
        budget = budget,
        genres = genres.map { it.toDomain() },
        homepage = homepage,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCountries = productionCountries.map { it.toDomain() },
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages.map { it.toDomain() },
        status = ProductionStatus.getType(status),
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

internal fun MoviePaginatedDto.toDomain(): MoviePaginated {
    return MoviePaginated(
        page = page,
        movies = movies.map { it.toDomain() },
        totalResults = totalResults,
        totalPages = totalPages
    )
}

internal fun GenreDto.toDomain() = Genre(id, name)

internal fun GenreListDto.toDomain() = genres.map { dto -> dto.toDomain() }

internal fun CountryDto.toDomain() = Country(code, name)

internal fun SpokenLanguageDto.toDomain() = SpokenLanguage(code, name)