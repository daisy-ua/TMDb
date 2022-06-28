package com.tmdb.repository.mappers

import com.tmdb.cache.entities.CountryEntity
import com.tmdb.cache.entities.GenreEntity
import com.tmdb.cache.entities.SpokenLanguageEntity
import com.tmdb.cache.entities.movies.MovieDetailsEntity
import com.tmdb.cache.entities.movies.MovieEntity
import com.tmdb.cache.entities.movies.MoviePaginatedEntity
import com.tmdb.network.models.CountryDto
import com.tmdb.network.models.genre.GenreDto
import com.tmdb.network.models.SpokenLanguageDto
import com.tmdb.network.models.movie.MovieDetailsDto
import com.tmdb.network.models.movie.MovieDto
import com.tmdb.network.models.movie.MoviePaginatedDto

internal fun MovieDto.toEntity(): MovieEntity {
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

internal fun MovieDetailsDto.toEntity(): MovieDetailsEntity {
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
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

internal fun MoviePaginatedDto.toEntity(): MoviePaginatedEntity {
    return MoviePaginatedEntity(
        page = page,
        movies = movies.map { it.toEntity() },
        totalResults = totalResults,
        totalPages = totalPages
    )
}

internal fun GenreDto.toEntity() = GenreEntity(id, name)

internal fun CountryDto.toEntity() = CountryEntity(code, name)

internal fun SpokenLanguageDto.toEntity() = SpokenLanguageEntity(code, name)
