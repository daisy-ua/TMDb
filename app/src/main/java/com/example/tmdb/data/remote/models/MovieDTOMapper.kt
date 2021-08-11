package com.example.tmdb.data.remote.models

import com.example.tmdb.data.models.Movie
import com.example.tmdb.utils.DomainMapper

class MovieDTOMapper: DomainMapper<MovieDTO, Movie> {
    override fun mapToDomainModel(model: MovieDTO): Movie {
        return Movie(
            id = model.id,
            title = model.title,
            originalTitle = model.originalTitle,
            voteAverage = model.voteAverage,
            releaseDate = model.releaseDate,
            posterPath = model.posterPath ?: "",
            overview = model.overview ?: "",
            genre = model.genres
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDTO {
        return MovieDTO(
            id = domainModel.id,
            title = domainModel.title,
            originalTitle = domainModel.originalTitle,
            voteAverage = domainModel.voteAverage,
            releaseDate = domainModel.releaseDate,
            posterPath = domainModel.posterPath,
            overview = domainModel.overview,
            genres = domainModel.genre
        )
    }

    fun toDomainList(initial: List<MovieDTO>): List<Movie> = initial.map { mapToDomainModel(it) }

    fun fromDomainList(initial: List<Movie>): List<MovieDTO> = initial.map { mapFromDomainModel(it) }

}