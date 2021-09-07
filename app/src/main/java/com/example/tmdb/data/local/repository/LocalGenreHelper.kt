package com.example.tmdb.data.local.repository

import com.example.tmdb.data.local.dao.GenreDao
import com.example.tmdb.data.local.mappers.GenreDBMapper
import com.example.tmdb.data.models.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalGenreHelper(
    private val dao: GenreDao,
    private val mapper: GenreDBMapper = GenreDBMapper()
) : LocalDataSource <String, Genre> {
    override fun getAll() : Flow<List<Genre>> = dao.getAll().map { list -> mapper.toDomainList(list) }

    override fun getById(vararg ids: Int) : Flow<List<Genre>> =
        dao.getById(*ids).map { list -> mapper.toDomainList(list) }

    override suspend fun insert(list: List<Genre>) = dao.insert(mapper.fromDomainList(list))

    override suspend fun deleteAll() = dao.deleteAll()
}