package com.example.tmdb.data.remote.mappers

import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.models.MovieDTO
import com.example.tmdb.utils.DomainMapper

class MovieDTOMapper: DomainMapper<MovieDTO, Movie> {
    override fun mapToDomainModel(model: MovieDTO): Movie =
        Movie(
            id = model.id,
            title = model.title,
            originalTitle = model.originalTitle,
            voteAverage = model.voteAverage,
            releaseDate = model.releaseDate.orEmpty(),
            posterPath = model.posterPath.orEmpty(),
            overview = model.overview.orEmpty(),
            genre = model.genres,
            runtime = model.runtime
        )

    override fun mapFromDomainModel(domainModel: Movie): MovieDTO =
        MovieDTO(
            id = domainModel.id,
            title = domainModel.title,
            originalTitle = domainModel.originalTitle,
            voteAverage = domainModel.voteAverage,
            releaseDate = domainModel.releaseDate,
            posterPath = domainModel.posterPath,
            overview = domainModel.overview,
            genres = domainModel.genre,
            runtime = domainModel.runtime
        )
}