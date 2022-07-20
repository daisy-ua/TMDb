package com.example.tmdb.ui.utils.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ContainerShowImplicitBinding
import com.example.tmdb.utils.ImageManager
import com.tmdb.models.movies.Movie

class MovieViewHolder(
    private val binding: ContainerShowImplicitBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) = with(binding) {
        showTitle.text = movie.title
        showVoteAverage.text = movie.voteAverage.toString()

        movie.posterPath?.let { ImageManager.getImage(showImage, it) }
    }
}