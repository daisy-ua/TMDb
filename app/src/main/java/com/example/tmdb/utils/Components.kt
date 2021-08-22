package com.example.tmdb.utils

import android.content.Context
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.models.Movie
import com.example.tmdb.databinding.ContainerRecyclerViewBinding
import com.example.tmdb.ui.adapters.MovieAdapter
import com.example.tmdb.utils.network.Resource
import com.example.tmdb.viewmodels.MainViewModel
import com.example.tmdb.viewmodels.MainViewModelFactory

fun getMainViewModel(owner: ViewModelStoreOwner) : MainViewModel {
    val viewModelProviderFactory = MainViewModelFactory()
    return ViewModelProvider(owner, viewModelProviderFactory)[MainViewModel::class.java]
}

fun setupRecyclerView(
    rv: RecyclerView,
    context: Context,
    adapter: RecyclerView.Adapter<*>,
    layout: GridLayoutManager = GridLayoutManager(context, 2)
) = with(rv) {
    setHasFixedSize(true)
    this.layoutManager = layout
    this.adapter = adapter
}

fun getRecyclerViewDataSetupObserver(contentList: ContainerRecyclerViewBinding) =
    Observer<Resource<List<Movie>>> { response ->
        with(contentList) {
            progressBar.isVisible = response is Resource.Loading && response.data.isNullOrEmpty()
            errorMsg.isVisible = response is Resource.Error && response.data.isNullOrEmpty()
            errorMsg.text = response.throwable?.localizedMessage

            response.data?.let { data ->
                (rv.adapter as MovieAdapter).run {
                    submitList(data)
                    notifyDataSetChanged()
                }
            }
        }
}