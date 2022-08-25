package com.example.tmdb.ui.utils.eventmanager

import androidx.lifecycle.ViewModel
import com.example.tmdb.ui.utils.eventmanager.events.BaseEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
open class EventManager @Inject constructor() : ViewModel() {
    private val _modificationEvents = MutableStateFlow<List<BaseEvents>>(emptyList())
    val modificationEvents = _modificationEvents

    var shouldApplyModifications = false

    fun emitEvent(events: BaseEvents) {
        _modificationEvents.value += events
    }
}