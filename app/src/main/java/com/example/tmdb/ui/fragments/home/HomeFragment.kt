package com.example.tmdb.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.daisy.constants.Response
import com.example.tmdb.R
import com.example.tmdb.constants.HomeCategory
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.components.recyclerview.setupRecyclerView
import com.example.tmdb.ui.utils.adapters.MovieAdapter
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.rvdecorators.LinearItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val nowPlayingShowsAdapter = MovieAdapter(this)
    private val topRatedShowsAdapter = MovieAdapter(this)
    private val trendingShowsAdapter = MovieAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupView()
        setupListeners()
        return binding.root
    }

    private fun setupView() {
        setupRecyclerView(binding.contentMain.nowPlayingShows, nowPlayingShowsAdapter)

        setupRecyclerView(binding.contentMain.trendingShows, trendingShowsAdapter)

        setupRecyclerView(binding.contentMain.topRatedShows, topRatedShowsAdapter)
    }

    private fun setupRecyclerView(rv: RecyclerView, adapter: MovieAdapter) {
        val itemDecoration =
            LinearItemDecoration(resources.getDimension(R.dimen.rv_item_margin_small))

        setupRecyclerView(
            rv,
            context,
            adapter,
            hasFixedSize = false,
            itemDecoration = itemDecoration
        )
    }

    private fun setupListeners() {

        binding.contentMain.nowPlayingSeeMore.setOnClickListener {
            navigateSeeMore(HomeCategory.NOW_PLAYING)
        }

        binding.contentMain.trendingSeeMore.setOnClickListener {
            navigateSeeMore(HomeCategory.TRENDING)
        }

        binding.contentMain.topRatedSeeMore.setOnClickListener {
            navigateSeeMore(HomeCategory.TOP_RATED)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                val nowPlayingIsNotLoading = viewModel.nowPlayingMovies.map {
                    it !is Response.Loading
                }

                val topRatedIsNotLoading = viewModel.topRatedMovies.map {
                    it !is Response.Loading
                }

                val trendingIsNotLoading = viewModel.trendingMovies.map {
                    it !is Response.Loading
                }

                val shouldHideLoader = combine(
                    nowPlayingIsNotLoading,
                    topRatedIsNotLoading,
                    trendingIsNotLoading,
                ) { (_nowPlayingIsNotLoading, _topRatedIsNotLoading, _trendingIsNotLoading) ->
                    _nowPlayingIsNotLoading && _topRatedIsNotLoading && _trendingIsNotLoading
                }.distinctUntilChanged()

                launch {
                    shouldHideLoader.collect { shouldHideLoader ->
                        binding.mainProgressBar.isVisible = !shouldHideLoader
                        binding.contentMain.root.isVisible = shouldHideLoader
                    }
                }

                launch {
                    viewModel.nowPlayingMovies.collect { response ->
                        response.data?.movies?.let {
                            nowPlayingShowsAdapter.submitList(it)
                        }
                    }
                }

                launch {
                    viewModel.trendingMovies.collect { response ->
                        response.data?.movies?.let {
                            trendingShowsAdapter.submitList(it)
                        }
                    }
                }

                launch {
                    viewModel.topRatedMovies.collect { response ->
                        response.data?.movies?.let {
                            topRatedShowsAdapter.submitList(it)
                        }
                    }
                }
            }
        }
    }

    private fun navigateSeeMore(category: HomeCategory) {
        val action = HomeFragmentDirections.getSeeMoreAction(category)
        findNavController().navigate(action)
    }

    override fun onItemClicked(id: Long) {
        val action = HomeFragmentDirections.getMovieDetailsAction(id)
        findNavController().navigate(action)
    }
}
