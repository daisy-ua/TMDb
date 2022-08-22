package com.tmdb.cache.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved")
data class MovieEntity(
    @PrimaryKey val id: Int,
)