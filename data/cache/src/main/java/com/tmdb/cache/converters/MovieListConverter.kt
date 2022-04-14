package com.tmdb.cache.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tmdb.cache.entities.movies.MovieEntity

class MovieListConverter {
    private val gson = Gson()

    @TypeConverter
    fun to(entitiesJson: String?): List<MovieEntity>? {
        if (entitiesJson.isNullOrEmpty()) return null

        val type = object : TypeToken<List<MovieEntity>>() {}.type
        return gson.fromJson(entitiesJson, type)
    }

    @TypeConverter
    fun from(entities: List<MovieEntity>): String? {
        if (entities.isNullOrEmpty()) return null

        val type = object : TypeToken<List<MovieEntity>>() {}.type
        return gson.toJson(entities, type)
    }
}
