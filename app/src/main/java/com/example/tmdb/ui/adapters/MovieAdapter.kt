package com.example.tmdb.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.models.Movie
import com.example.tmdb.databinding.ContainerMovieRvItemBinding
import com.example.tmdb.utils.DiffUtilCallback
import com.example.tmdb.utils.network.ImageManager

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ContainerMovieRvItemBinding
    private val diffCallback = DiffUtilCallback()
    private val differ = AsyncListDiffer(this,diffCallback)

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = differ.currentList[position].id.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ContainerMovieRvItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        when (holder) {
            is ItemViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<Movie>) {
        differ.submitList(list)
    }

    inner class ItemViewHolder(private val item_binding: ContainerMovieRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(item_binding) {
            movieTitle.text = movie.title
            movieVoteAverage.text = movie.voteAverage.toString()

            ImageManager.getImage(movieImage, movie.posterPath)
        }
    }
}