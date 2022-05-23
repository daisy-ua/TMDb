package com.example.tmdb.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.components.getRecyclerViewDataSetupObserver
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.components.MarginItemDecoration
import com.example.tmdb.ui.components.setupRecyclerView
import com.example.tmdb.ui.utils.adapters.MovieAdapter
import com.example.tmdb.ui.utils.interactions.Interaction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        setupListeners()
        return binding.root
    }

    private fun setupRecyclerView() {
        setupRecyclerView(binding.nowPlayingShows.rv)
        setupRecyclerView(binding.popularShows.rv)
        setupRecyclerView(binding.topRatedShows.rv)
    }

    private fun setupRecyclerView(rv: RecyclerView) {
        val itemDecoration = MarginItemDecoration(resources.getDimension(R.dimen.rv_item_margin))

        setupRecyclerView(
            rv,
            context,
            MovieAdapter(this),
            itemDecoration = itemDecoration
        )
    }

    private fun setupListeners() {
        viewModel.popularMovies.observe(viewLifecycleOwner,
            getRecyclerViewDataSetupObserver(binding.popularShows))
        viewModel.topRatedMovies.observe(viewLifecycleOwner,
            getRecyclerViewDataSetupObserver(binding.topRatedShows))
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner,
            getRecyclerViewDataSetupObserver(binding.nowPlayingShows))
    }

    override fun onItemClicked(id: Long) {
        val action = HomeFragmentDirections.getMovieDetailsAction(id)
        findNavController().navigate(action)
    }
}
