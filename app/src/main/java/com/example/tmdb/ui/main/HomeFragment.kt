package com.example.tmdb.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.adapters.MovieAdapter
import com.example.tmdb.ui.interactions.Interaction
import com.example.tmdb.viewmodels.getMainViewModel
import com.example.tmdb.utils.components.getRecyclerViewDataSetupObserver
import com.example.tmdb.utils.components.setupRecyclerView
import com.example.tmdb.viewmodels.MainViewModel

class HomeFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by lazy { getMainViewModel(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupRecyclerView(binding.contentList.rv, requireContext(), MovieAdapter(this))
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        viewModel.popularMoviesResponse.observe(
            viewLifecycleOwner,
            getRecyclerViewDataSetupObserver(binding.contentList)
        )
    }

    override fun onItemClicked(position: Int) {
        val item = (binding.contentList.rv.adapter as MovieAdapter).getItem(position)
        val action = HomeFragmentDirections.getMovieDetailsAction(item)
        findNavController().navigate(action)
    }
}