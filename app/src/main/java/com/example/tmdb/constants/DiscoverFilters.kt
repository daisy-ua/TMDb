package com.example.tmdb.constants

class DiscoverFilters {
    var sortBy: String? = null
        private set

    var withGenres: String? = null
        private set

    fun setSortBy(sortByPosition: Int?, isAscending: Boolean = false) {
        sortByPosition?.let {
            this.sortBy =
                SortOption.values()[sortByPosition].id + if (isAscending) ".asc" else ".desc"
        }
    }

    fun setWithGenres(genreIds: List<Int>) {
        this.withGenres = genreIds.joinToString { "$it" }
    }
}