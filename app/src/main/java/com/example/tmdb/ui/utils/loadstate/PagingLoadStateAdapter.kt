package com.example.tmdb.ui.utils.loadstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.example.tmdb.R
import com.example.tmdb.databinding.ItemNetworkStateBinding

class PagingLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemNetworkStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_network_state, parent, false)
            )
        ) {
            retry.invoke()
        }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)
}