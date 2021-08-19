package com.example.tmdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.models.Movie
import com.example.tmdb.databinding.ContainerMovieRvItemBinding
import com.example.tmdb.utils.DiffUtilCallback
import com.example.tmdb.utils.ImageManager

class MovieAdapter : ListAdapter<Movie, MovieAdapter.ItemViewHolder>(DiffUtilCallback()) {
    private lateinit var binding: ContainerMovieRvItemBinding

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ContainerMovieRvItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = currentList.size

    inner class ItemViewHolder(private val item_binding: ContainerMovieRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(item_binding) {
            movieTitle.text = movie.title
//            movieDescription.text = movie.overview
//            movieGenres.text = movie.genre.toString() //TODO: replace with names
            movieVoteAverage.text = movie.voteAverage.toString()

            ImageManager.getImage(movieImage as AppCompatImageView, movie.posterPath)
        }
    }
}