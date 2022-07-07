package com.example.tmdb.ui.utils

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebounceQueryTextChange: (String?) -> Unit,
) : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private var debouncePeriod: Long = 200

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(query: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            query?.let {
                delay(debouncePeriod)
                onDebounceQueryTextChange(query)
            }
        }
        return false
    }
}