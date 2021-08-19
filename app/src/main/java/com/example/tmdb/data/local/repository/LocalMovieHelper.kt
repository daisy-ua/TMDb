package com.example.tmdb.data.local.repository

import com.example.tmdb.data.local.dao.MovieDao
import com.example.tmdb.data.local.mappers.MovieDBMapper
import com.example.tmdb.data.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMovieHelper(
    private val dao: MovieDao,
    private val mapper: MovieDBMapper = MovieDBMapper()
) {
    fun getPopular() : Flow<List<Movie>> = dao.getPopular().map { list -> mapper.toDomainList(list) }

    suspend fun insert(list: List<Movie>) = dao.insertMovies(mapper.fromDomainList(list))

    suspend fun deleteAll() = dao.deleteAllMovies()
}