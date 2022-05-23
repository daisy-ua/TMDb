package com.example.tmdb.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ContainerShowImplicitBinding
import com.example.tmdb.ui.utils.adapters.viewholders.MovieViewHolder
import com.example.tmdb.ui.utils.interactions.Interaction
import com.tmdb.models.movies.Movie

class MovieAdapter(
    private val interaction: Interaction,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ContainerShowImplicitBinding
    private val diffCallback = DiffUtilCallback()
    private val differ = AsyncListDiffer(this, diffCallback)

    init {
        setHasStableIds(true)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = differ.currentList[position].id.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ContainerShowImplicitBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        when (holder) {
            is MovieViewHolder -> holder.bind(item)
        }
        holder.itemView.setOnClickListener { interaction.onItemClicked(getItemId(position)) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<Movie>) = differ.submitList(list)
}
