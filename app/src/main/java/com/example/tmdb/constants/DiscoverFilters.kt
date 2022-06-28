package com.example.tmdb.constants

class DiscoverFilters private constructor(
    val sortBy: String?,
    val withGenres: String?,
) {
    data class Builder(
        private var sortBy: String? = null,
        private var withGenres: String? = null,
    ) {
        fun sortBy(option: String) = apply { this.sortBy = option }

        fun withGenres(genreIds: List<Int>) =
            apply { this.withGenres = genreIds.joinToString { "$it" } }

        fun build() = DiscoverFilters(sortBy, withGenres)
    }
}