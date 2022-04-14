package com.tmdb.repository.mappers

import com.tmdb.cache.entities.CountryEntity
import com.tmdb.cache.entities.GenreEntity
import com.tmdb.cache.entities.SpokenLanguageEntity
import com.tmdb.cache.entities.movies.MovieDetailsEntity
import com.tmdb.cache.entities.movies.MovieEntity
import com.tmdb.cache.entities.movies.MoviePaginatedEntity
import com.tmdb.models.Country
import com.tmdb.models.Genre
import com.tmdb.models.ProductionStatus
import com.tmdb.models.SpokenLanguage
import com.tmdb.models.movies.Movie
import com.tmdb.models.movies.MovieDetails
import com.tmdb.models.movies.MoviePaginated

internal fun MovieEntity.toDomain(): Movie {
    return Movie(
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

internal fun MovieDetailsEntity.toDomain(): MovieDetails {
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

internal fun MoviePaginatedEntity.toDomain(): MoviePaginated {
    return MoviePaginated(
        page = page,
        movies = movies.map { it.toDomain() },
        totalResults = totalResults,
        totalPages = totalPages
    )
}

internal fun GenreEntity.toDomain() = Genre(id, name)

internal fun CountryEntity.toDomain() = Country(code, name)

internal fun SpokenLanguageEntity.toDomain() = SpokenLanguage(code, name)
