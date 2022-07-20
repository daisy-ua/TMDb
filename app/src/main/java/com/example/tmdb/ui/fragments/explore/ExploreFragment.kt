package com.example.tmdb.ui.fragments.explore

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentExploreBinding
import com.example.tmdb.ui.components.recyclerview.setupRecyclerView
import com.example.tmdb.ui.fragments.explore.filters.ExploreFiltersFragment
import com.example.tmdb.ui.utils.DebounceQueryTextListener
import com.example.tmdb.ui.utils.adapters.MoviePagingAdapter
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.loadstate.PagingLoadStateAdapter
import com.example.tmdb.ui.utils.rvdecorators.GridItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentExploreBinding

    private val viewModel: ExploreViewModel by viewModels()

    private val adapter = MoviePagingAdapter(this)

    private val pagingAdapter = adapter.withLoadStateFooter(
        footer = PagingLoadStateAdapter(adapter::retry)
    )

    private var discoverJob: Job? = null

    private val filtersDialog: ExploreFiltersFragment by lazy {
        ExploreFiltersFragment()
    }

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

        try {
            setupRecyclerView(
                binding.searchResult.rv,
                context,
                pagingAdapter,
                GridLayoutManager(
                    context,
                    3
                ),
                false,
                itemDecoration = itemDecoration
            )
        } catch (ex: Exception) {
            Log.d("daisy", ex.message.toString())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding.filtersBtn.setOnClickListener { showFiltersDialog() }

        viewModel.filters.observe(viewLifecycleOwner) { discoverMovies() }

        binding.searchBar.setOnQueryTextListener(
            DebounceQueryTextListener(viewLifecycleOwner.lifecycle, search)
        )

        binding.root.setOnTouchListener { _, _ -> hideSoftKeyboard() }

        adapter.addLoadStateListener(::loadState)
    }

    private fun discoverMovies() {
        discoverJob?.cancel()
        discoverJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchMovieDiscoverResult().collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    private fun searchMovies(query: String) {
        discoverJob?.cancel()
        discoverJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchMovieSearchResult(query).collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    private val search: (String?) -> Unit = { _query ->
        _query?.let { query ->
            if (_query.isEmpty()) {
                binding.searchResult.rv.adapter = pagingAdapter
                discoverMovies()
                binding.filtersBtn.visibility = View.VISIBLE
            } else {
                binding.searchResult.rv.adapter = adapter
                searchMovies(query)
                binding.filtersBtn.visibility = View.GONE
            }
        }
    }

    private fun loadState(loadState: CombinedLoadStates) {
        binding.notFoundMsg.isVisible =
            loadState.refresh is LoadState.NotLoading && adapter.itemCount < 1

        binding.searchResult.rv.isVisible =
            loadState.source.refresh is LoadState.NotLoading

        binding.searchResult.progressBar.isVisible =
            loadState.source.refresh is LoadState.Loading
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

    override fun onItemClicked(id: Long) {
        val action = ExploreFragmentDirections.getMovieDetailsAction(id)
        findNavController().navigate(action)
    }
}