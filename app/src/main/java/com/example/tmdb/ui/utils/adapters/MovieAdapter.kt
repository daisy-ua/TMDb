package com.example.tmdb.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ContainerMovieRvItemBinding
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.utils.ImageManager
import com.tmdb.models.movies.Movie

class MovieAdapter(
    val interaction: Interaction
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ContainerMovieRvItemBinding
    private val diffCallback = DiffUtilCallback()
    private val differ = AsyncListDiffer(this, diffCallback)

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

    fun submitList(list: List<Movie>) = differ.submitList(list)

    // TODO: remove outside the adapter
    inner class ItemViewHolder(private val itemBinding: ContainerMovieRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(itemBinding) {
            movieTitle.text = movie.title
            movieVoteAverage.text = movie.voteAverage.toString()

            movie.posterPath?.let { ImageManager.getImage(movieImage, it) }

            itemView.setOnClickListener { interaction.onItemClicked(adapterPosition) }
        }
    }
}
