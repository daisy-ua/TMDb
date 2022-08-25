package com.example.tmdb.ui.utils.eventmanager.events

sealed class BaseEvents {

    data class Remove(val id: Int) : BaseEvents()

    data class InsertItemHeader(val item: Any) : BaseEvents()
}