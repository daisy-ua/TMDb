package com.example.tmdb.data.local.mappers

import com.example.tmdb.data.local.entities.MovieDB
import com.example.tmdb.data.models.Movie
import com.example.tmdb.utils.DomainMapper

class MovieDBMapper : DomainMapper<MovieDB, Movie> {
    override fun mapToDomainModel(model: MovieDB): Movie =
        Movie(model.uid, model.title, model.originalTitle, model.voteAverage,
            model.releaseDate, model.posterPath, model.overview, model.genre, model.runtime)

    override fun mapFromDomainModel(domainModel: Movie): MovieDB =
        MovieDB(domainModel.id, domainModel.title, domainModel.originalTitle, domainModel.voteAverage,
            domainModel.releaseDate, domainModel.posterPath, domainModel.overview,
            domainModel.genre, domainModel.runtime)
}