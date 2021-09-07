package com.example.tmdb.data.remote.repositories

import com.example.tmdb.data.models.Movie
import com.example.tmdb.data.remote.services.MovieServices
import com.example.tmdb.data.remote.mappers.MovieDTOMapper
import com.example.tmdb.data.remote.mappers.MovieDetailDTOMapper

class RemoteMovieHelper(
    private val movieService: MovieServices,
    private val mapper: MovieDTOMapper = MovieDTOMapper()
) : RemoteAdvancedDataSource<Movie> {
    private val detailMapper = MovieDetailDTOMapper()

    override suspend fun getDetails(id: Int): Movie =
        detailMapper.mapToDomainModel(movieService.getMovieDetails(id))

    override suspend fun search(query: String): List<Movie> =
        mapper.toDomainList(movieService.searchMovies(query).results)

    override suspend fun discover(genre_ids: String): List<Movie> =
        mapper.toDomainList(movieService.discoverMovies(genre_ids).results)

    override suspend fun getAll(): List<Movie> =
        mapper.toDomainList(movieService.getPopularMovies().results)

    override suspend fun getSimilar(id: Int): List<Movie> =
        mapper.toDomainList(movieService.getSimilarMovies(id).results)
}