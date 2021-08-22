package com.example.tmdb.data.local.repository

import com.example.tmdb.data.local.dao.MovieDao
import com.example.tmdb.data.local.mappers.MovieDBMapper
import com.example.tmdb.data.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMovieHelper(
    private val dao: MovieDao,
    private val mapper: MovieDBMapper = MovieDBMapper()
) : LocalDataSource<String, Movie> {
    override fun getAll() : Flow<List<Movie>> = dao.getAll().map { list -> mapper.toDomainList(list) }

    override suspend fun insert(list: List<Movie>) = dao.insert(mapper.fromDomainList(list))

    override suspend fun deleteAll() = dao.deleteAll()
}