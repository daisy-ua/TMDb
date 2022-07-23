package com.example.tmdb.ui.utils.uistate

import com.example.tmdb.constants.AppConstants.DEFAULT_QUERY

data class UiState(
    val query: String = DEFAULT_QUERY,
    val filters: Map<String, *>?,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false,
)