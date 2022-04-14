package com.tmdb.cache.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tmdb.cache.entities.GenreEntity

class GenreListConverter {
    private val gson = Gson()

    @TypeConverter
    fun to(entitiesJson: String?): List<GenreEntity>? {
        if (entitiesJson.isNullOrEmpty()) return null

        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return gson.fromJson(entitiesJson, type)
    }

    @TypeConverter
    fun from(entities: List<GenreEntity>): String? {
        if (entities.isNullOrEmpty()) return null

        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return gson.toJson(entities, type)
    }
}
