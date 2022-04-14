package com.tmdb.cache.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tmdb.cache.entities.CountryEntity

class CountryListConverter {
    private val gson = Gson()

    @TypeConverter
    fun to(entitiesJson: String?): List<CountryEntity>? {
        if (entitiesJson.isNullOrEmpty()) return null

        val type = object : TypeToken<List<CountryEntity>>() {}.type
        return gson.fromJson(entitiesJson, type)
    }

    @TypeConverter
    fun from(entities: List<CountryEntity>): String? {
        if (entities.isNullOrEmpty()) return null

        val type = object : TypeToken<List<CountryEntity>>() {}.type
        return gson.toJson(entities, type)
    }
}
