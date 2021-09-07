package com.example.tmdb.data.remote.mappers

import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.models.MovieDetailDTO
import com.example.tmdb.utils.DomainMapper

class MovieDetailDTOMapper: DomainMapper<MovieDetailDTO, Movie> {
    override fun mapToDomainModel(model: MovieDetailDTO): Movie =
        Movie(
            id = model.id,
            title = model.title,
            originalTitle = model.originalTitle,
            voteAverage = model.voteAverage,
            releaseDate = model.releaseDate.orEmpty(),
            posterPath = model.posterPath.orEmpty(),
            overview = model.overview.orEmpty(),
            genre = model.getGenreId(),
            runtime = model.runtime
        )

    override fun mapFromDomainModel(domainModel: Movie): MovieDetailDTO =
        MovieDetailDTO(
            id = domainModel.id,
            title = domainModel.title,
            originalTitle = domainModel.originalTitle,
            voteAverage = domainModel.voteAverage,
            releaseDate = domainModel.releaseDate,
            posterPath = domainModel.posterPath,
            overview = domainModel.overview,
            genreIds = domainModel.genre,
            runtime = domainModel.runtime
        )
}