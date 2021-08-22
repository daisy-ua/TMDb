package com.example.tmdb.data.local.mappers

import com.example.tmdb.data.local.entities.GenreDB
import com.example.tmdb.data.models.Genre
import com.example.tmdb.utils.DomainMapper

class GenreDBMapper : DomainMapper<GenreDB, Genre> {
    override fun mapToDomainModel(model: GenreDB): Genre =
        Genre(model.id, model.name)

    override fun mapFromDomainModel(domainModel: Genre): GenreDB =
        GenreDB(domainModel.id, domainModel.name)
}