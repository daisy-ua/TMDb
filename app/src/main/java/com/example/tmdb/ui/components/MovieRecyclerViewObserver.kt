package com.example.tmdb.ui.components

import android.util.Log
import androidx.lifecycle.Observer
import com.example.tmdb.databinding.ContainerRecyclerViewBinding
import com.example.tmdb.ui.utils.adapters.MovieAdapter
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response

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