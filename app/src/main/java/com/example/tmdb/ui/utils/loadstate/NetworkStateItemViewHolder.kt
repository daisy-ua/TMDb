package com.example.tmdb.ui.utils.loadstate

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.ItemNetworkStateBinding
import javax.inject.Inject

class NetworkStateItemViewHolder @Inject constructor(
    private val binding: ItemNetworkStateBinding,
    private val retryCallback: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retryCallback() }
    }

    fun bind(loadState: LoadState) {
        with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
//            errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            errorMsg.text = "No Internet Connection"
        }

        binding.retryButton.isVisible = loadState !is LoadState.Loading
    }
}
