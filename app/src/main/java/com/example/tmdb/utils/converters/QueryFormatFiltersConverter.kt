package com.example.tmdb.utils.converters

import com.example.tmdb.constants.SortOption

object QueryFormatFiltersConverter {
    fun getSortBy(sortByPosition: Int, isAscending: Boolean = false) =
        SortOption.values()[sortByPosition].id + if (isAscending) ".asc" else ".desc"

    fun getWithGenres(genreIds: List<Int>) = genreIds.joinToString { "$it" }
}