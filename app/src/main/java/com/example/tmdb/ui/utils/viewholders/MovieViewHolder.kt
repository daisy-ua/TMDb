package com.example.tmdb.ui.utils.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.daisy.domain.models.movies.Movie
import com.example.tmdb.databinding.ContainerShowImplicitBinding
import com.example.tmdb.utils.ImageManager

class MovieViewHolder(
    private val binding: ContainerShowImplicitBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) = with(binding) {
        showTitle.text = movie.title
        showVoteAverage.text = movie.voteAverage.toString()

        ImageManager.getImage(showImage, movie.posterPath)
    }
}