package com.example.tmdb.utils

import com.example.tmdb.constants.FilterKeys
import com.example.tmdb.constants.SortOption

fun getFilterMap(
    sortBy: Int,
    withGenres: List<Int>,
) = mutableMapOf(
    FilterKeys.SORT_BY to sortBy,

    FilterKeys.WITH_GENRES to withGenres
)

private val queryFormatConverter = QueryFormatFiltersConverter

@Suppress("UNCHECKED_CAST")
fun getFiltersFormatted(
    source: Map<String, *>?,
) = mutableMapOf<String, String>().apply {
    source?.let { filters ->
        (filters[FilterKeys.SORT_BY] as Int).let { sortBy ->
            if (sortBy != -1) {
                put(FilterKeys.SORT_BY, queryFormatConverter.getSortBy(sortBy))
            }
        }

        (filters[FilterKeys.WITH_GENRES] as List<Int>?)?.let { withGenres ->
            put(FilterKeys.WITH_GENRES, queryFormatConverter.getWithGenres(withGenres))
        }
    }
}

private object QueryFormatFiltersConverter {
    fun getSortBy(sortByPosition: Int, isAscending: Boolean = false) =
        SortOption.values()[sortByPosition].id + if (isAscending) ".asc" else ".desc"

    fun getWithGenres(genreIds: List<Int>) = genreIds.joinToString { "$it" }
}