package com.example.tmdb.ui.components.recyclerview

import android.util.Log
import androidx.lifecycle.Observer
import com.daisy.constants.Response
import com.daisy.domain.models.movies.MoviePaginated
import com.example.tmdb.databinding.ContainerRecyclerViewBinding
import com.example.tmdb.ui.utils.adapters.MovieAdapter

fun getRecyclerViewDataSetupObserver(contentList: ContainerRecyclerViewBinding) =
    Observer<Response<MoviePaginated>?> { response ->
        when (response) {
            is Response.Success -> {
                response.data?.movies?.let { movies ->
                    (contentList.rv.adapter as MovieAdapter).submitList(movies)
                }
            }

            is Response.Error -> {
                Log.d("daisy", "error")
            }

            is Response.Loading -> {
                Log.d("daisy", "loading")
            }
        }
    }