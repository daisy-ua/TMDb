package com.example.tmdb.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daisy.domain.models.movies.Movie
import com.example.tmdb.databinding.ContainerShowImplicitBinding
import com.example.tmdb.ui.utils.MovieComparator
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.viewholders.MovieViewHolder
import javax.inject.Inject

class MoviePagingAdapter @Inject constructor(
    private val interaction: Interaction,
) : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieComparator) {
    private lateinit var binding: ContainerShowImplicitBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ContainerShowImplicitBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            if (holder is MovieViewHolder) {
                holder.bind(movie)
                holder.itemView.setOnClickListener {
                    interaction.onItemClicked(movie.id!!.toLong())
                }
            }
        }
    }
}