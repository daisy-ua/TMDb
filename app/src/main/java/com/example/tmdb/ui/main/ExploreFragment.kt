package com.example.tmdb.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.data.models.Genre
import com.example.tmdb.databinding.FragmentExploreBinding
import com.example.tmdb.ui.adapters.MovieAdapter
import com.example.tmdb.ui.interactions.Interaction
import com.example.tmdb.utils.components.getRecyclerViewDataSetupObserver
import com.example.tmdb.utils.components.setupRecyclerView
import com.example.tmdb.utils.extensions.observeOnce
import com.example.tmdb.utils.network.Resource
import com.example.tmdb.viewmodels.ExploreViewModel
import com.example.tmdb.viewmodels.ExploreViewModelFactory
import com.example.tmdb.viewmodels.getViewModel
import com.google.android.material.tabs.TabLayout

class ExploreFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel

    init {
        lifecycleScope.launchWhenCreated {
            viewModel = getViewModel(requireActivity(), ExploreViewModelFactory())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        setupListeners()
        setupView()
        return binding.root
    }

    private fun setupView() {
        setupRecyclerView(binding.contentList.rv, requireContext(), MovieAdapter(this))
    }

    private fun setupListeners() {
        binding.genresBar.addOnTabSelectedListener(onTabSelectedListener)

        binding.contentList.rv.apply {
            addOnScrollListener(onRecyclerViewScrollListener(this))
        }

        viewModel.genresResponse.observeOnce(viewLifecycleOwner, tabViewObserver)

        viewModel.moviesByGenre.observe(
            viewLifecycleOwner, getRecyclerViewDataSetupObserver(binding.contentList)
        )
    }

    private val tabViewObserver = Observer<Resource<List<Genre>>> { response ->
        response.data?.let { genres ->
            with(binding.genresBar) {
                for (item in genres)
                    addTab(newTab().setText(item.name).setId(item.id))

                tabMode = if (tabCount == 3) TabLayout.MODE_FIXED
                else TabLayout.MODE_SCROLLABLE
            }
        }
    }

    private fun onRecyclerViewScrollListener(rv: RecyclerView) =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rv.canScrollVertically(1))
                    viewModel.loadNextPage()
            }
        }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        @SuppressLint("NotifyDataSetChanged")
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let { genre ->
                viewModel.resetMoviesByGenre()
                viewModel.genreIds = genre.id.toString()
                viewModel.loadNextPage()
                binding.contentList.rv.adapter?.notifyDataSetChanged()
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    override fun onItemClicked(position: Int) {
        val item = (binding.contentList.rv.adapter as MovieAdapter).getItemId(position)
        val action = ExploreFragmentDirections.getMovieDetailsAction(item)
        findNavController().navigate(action)
    }
}