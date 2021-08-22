package com.example.tmdb.utils.cache

data class CacheEntry<T>(
    val key: String,
    val value: T,
    val createdAt: Long = System.currentTimeMillis()
)