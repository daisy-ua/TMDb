package com.example.tmdb.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreDB(@PrimaryKey val id: Int, val name: String)