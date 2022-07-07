package com.example.tmdb.ui.fragments.explore

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentExploreBinding
import com.example.tmdb.ui.components.getRecyclerViewDataSetupObserver
import com.example.tmdb.ui.components.setupRecyclerView
import com.example.tmdb.ui.fragments.explore.filters.ExploreFiltersFragment
import com.example.tmdb.ui.utils.DebounceQueryTextListener
import com.example.tmdb.ui.utils.adapters.MovieAdapter
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.rvdecorators.GridItemDecorator
import com.tmdb.models.movies.MoviePaginated
import com.tmdb.repository.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentExploreBinding
    private val viewModel: ExploreViewModel by viewModels()

    private val filtersDialog = ExploreFiltersFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        setupView()
        setupListeners()
        return binding.root
    }

    private fun setupView() {

        val itemDecoration =
            GridItemDecorator(3, resources.getDimension(R.dimen.rv_item_margin))

        setupRecyclerView(
            binding.searchResult.rv,
            context,
            MovieAdapter(this),
            GridLayoutManager(
                context,
                3
            ),
            false,
            itemDecoration = itemDecoration
        )

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding.filtersBtn.setOnClickListener { showFiltersDialog() }

        viewModel.movies.observe(viewLifecycleOwner,
            getRecyclerViewDataSetupObserver(binding.searchResult))

        viewModel.movies.observe(viewLifecycleOwner, notFoundMsgObserver)

        binding.searchBar.setOnQueryTextListener(
            DebounceQueryTextListener(viewLifecycleOwner.lifecycle, search)
        )

        binding.root.setOnTouchListener { _, _ -> hideSoftKeyboard() }
    }

    private val notFoundMsgObserver = Observer<Response<MoviePaginated>?> { response ->
        if (response?.data?.movies?.isEmpty() == true) {
            binding.notFoundMsg.visibility = View.VISIBLE
        } else {
            binding.searchResult.rv.scrollToPosition(0)
            binding.notFoundMsg.visibility = View.GONE
        }
    }

    private val search: (String?) -> Unit = { _query ->
        _query?.let { query ->
            if (_query.isEmpty()) {
                viewModel.fetchMovies()
                binding.filtersBtn.visibility = View.VISIBLE
            } else {
                viewModel.fetchSearchedMovies(query)
                binding.filtersBtn.visibility = View.GONE
            }
        }
    }

    private fun hideSoftKeyboard(): Boolean {
        val inputManager: InputMethodManager =
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputManager.hideSoftInputFromWindow(
            binding.searchBar.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun showFiltersDialog() {
        if (!filtersDialog.isVisible) {
            filtersDialog.show(childFragmentManager, ExploreFiltersFragment.TAG)
        }
    }

    override fun onItemClicked(position: Long) {
        TODO("Not yet implemented")
    }
}