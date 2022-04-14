package com.tmdb.repository.mappers

import com.tmdb.cache.entities.CountryEntity
import com.tmdb.cache.entities.GenreEntity
import com.tmdb.cache.entities.SpokenLanguageEntity
import com.tmdb.cache.entities.movies.MovieDetailsEntity
import com.tmdb.cache.entities.movies.MovieEntity
import com.tmdb.cache.entities.movies.MoviePaginatedEntity
import com.tmdb.models.Country
import com.tmdb.models.Genre
import com.tmdb.models.SpokenLanguage
import com.tmdb.models.movies.Movie
import com.tmdb.models.movies.MovieDetails
import com.tmdb.models.movies.MoviePaginated

internal fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        posterPath = posterPath,
        adult = adult,
        overview = overview,
        releaseDate = releaseDate,
        genreIds = genreIds,
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

internal fun MovieDetails.toEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        adult = adult,
        backdropPath = backdropPath,
        budget = budget,
        genres = genres.map { it.toEntity() },
        homepage = homepage,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCountries = productionCountries.map { it.toEntity() },
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = spokenLanguages.map { it.toEntity() },
        status = status.status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

internal fun MoviePaginated.toEntity(): MoviePaginatedEntity {
    return MoviePaginatedEntity(
        page = page,
        movies = movies.map { it.toEntity() },
        totalResults = totalResults,
        totalPages = totalPages
    )
}

internal fun Genre.toEntity() = GenreEntity(id, name)

internal fun Country.toEntity() = CountryEntity(code, name)

internal fun SpokenLanguage.toEntity() = SpokenLanguageEntity(code, name)
