package com.example.tmdb.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tmdb.databinding.FragmentExploreBinding
import com.example.tmdb.ui.adapters.MovieAdapter
import com.example.tmdb.ui.interactions.Interaction
import com.example.tmdb.utils.getMainViewModel
import com.example.tmdb.utils.getRecyclerViewDataSetupObserver
import com.example.tmdb.utils.setupRecyclerView
import com.example.tmdb.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout

class ExploreFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentExploreBinding
    private val viewModel: MainViewModel by lazy { getMainViewModel(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        setupView()
        setupListeners()
        return binding.root
    }

    private fun setupView() {
        setupTabView()
        setupRecyclerView(binding.contentList.rv, requireContext(), MovieAdapter(this))
    }

    private fun setupListeners() {
        binding.genresBar.addOnTabSelectedListener(onTabSelectedListener)
        viewModel.moviesByGenre.observe(
            viewLifecycleOwner, getRecyclerViewDataSetupObserver(binding.contentList))
    }

    private fun setupTabView() = with(binding.genresBar) {
        for (item in viewModel.genresResponse.value?.data.orEmpty())
            addTab(newTab().setText(item.name).setId(item.id))

        tabMode = if (tabCount == 3) TabLayout.MODE_FIXED else TabLayout.MODE_SCROLLABLE
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        @SuppressLint("NotifyDataSetChanged")
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let { genre ->
                viewModel.discoverMovies(genre.id.toString())
                binding.contentList.rv.adapter?.notifyDataSetChanged()
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    override fun onItemClicked(position: Int) {
        val item = (binding.contentList.rv.adapter as MovieAdapter).getItem(position)
        val action = ExploreFragmentDirections.getMovieDetailsAction(item)
        findNavController().navigate(action)
    }
}