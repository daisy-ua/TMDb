package com.example.tmdb.ui.utils.uistate

sealed class UiAction {
    data class Search(
        val query: String,
        val filters: Map<String, *>? = null,
    ) : UiAction()

    data class Scroll(val currentQuery: String) : UiAction()
}