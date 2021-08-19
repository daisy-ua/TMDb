package com.example.tmdb.data.local.converters

import androidx.room.TypeConverter

object IntListConverter {

    private const val separator = ","

    @TypeConverter
    @JvmStatic
    fun toList(data: List<Int>) : String = data.joinToString(separator = separator)

    @TypeConverter
    @JvmStatic
    fun fromList(dataString: String) : List<Int>  =
        dataString.split(separator).map { it.trim() }.map { it.toInt() }
}