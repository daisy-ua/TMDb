package com.tmdb.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "saved")
data class MovieEntity(
    @PrimaryKey val id: Int,

    @ColumnInfo(name = "date_added")
    val dateAdded: LocalDateTime
)