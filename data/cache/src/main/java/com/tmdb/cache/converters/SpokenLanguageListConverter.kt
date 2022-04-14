package com.tmdb.cache.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tmdb.cache.entities.SpokenLanguageEntity

class SpokenLanguageListConverter {
    private val gson = Gson()

    @TypeConverter
    fun to(entitiesJson: String?): List<SpokenLanguageEntity>? {
        if (entitiesJson.isNullOrEmpty()) return null

        val type = object : TypeToken<List<SpokenLanguageEntity>>() {}.type
        return gson.fromJson(entitiesJson, type)
    }

    @TypeConverter
    fun from(entities: List<SpokenLanguageEntity>): String? {
        if (entities.isNullOrEmpty()) return null

        val type = object : TypeToken<List<SpokenLanguageEntity>>() {}.type
        return gson.toJson(entities, type)
    }
}
