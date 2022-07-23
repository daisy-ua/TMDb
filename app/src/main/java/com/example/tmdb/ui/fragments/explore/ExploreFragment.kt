package com.example.tmdb.ui.fragments.explore

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentExploreBinding
import com.example.tmdb.ui.components.recyclerview.setupRecyclerView
import com.example.tmdb.ui.fragments.explore.filters.ExploreFiltersFragment
import com.example.tmdb.ui.utils.adapters.MoviePagingAdapter
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.loadstate.PagingLoadStateAdapter
import com.example.tmdb.ui.utils.rvdecorators.GridItemDecorator
import com.example.tmdb.ui.utils.uistate.UiAction
import com.example.tmdb.ui.utils.uistate.UiState
import com.tmdb.models.movies.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentExploreBinding

    private val viewModel: ExploreViewModel by viewModels()

    private val adapter = MoviePagingAdapter(this)

    private val pagingAdapter = adapter.withLoadStateFooter(
        footer = PagingLoadStateAdapter(adapter::retry)
    )

    private val filtersDialog: ExploreFiltersFragment by lazy {
        ExploreFiltersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        setupListView()

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )

        return binding.root
    }

    private fun setupListView() {
        val itemDecoration =
            GridItemDecorator(3, resources.getDimension(R.dimen.rv_item_margin_small))

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

    override fun onStop() {
        viewModel.saveCurrentDataState()
        super.onStop()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun FragmentExploreBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Movie>>,
        uiActions: (UiAction) -> Unit,
    ) {
        root.setOnTouchListener { _, _ -> hideSoftKeyboard() }

        filtersBtn.setOnClickListener { showFiltersDialog() }

        bindSearchView(
            uiState = uiState,
            onQueryChanged = uiActions
        )

        bindRecyclerView(
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun FragmentExploreBinding.bindSearchView(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction) -> Unit,
    ) {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    onQueryChanged(UiAction.Search(
                        query = query,
                        filters = uiState.value.filters ?: uiState.value.filters
                    ))
                }
                return false
            }
        })
    }

    private fun FragmentExploreBinding.bindRecyclerView(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Movie>>,
        onScrollChanged: (UiAction) -> Unit,
    ) {
        searchResult.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = adapter.loadStateFlow
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val hasFiltersUpdated = uiState
            .map { it.filters }
            .map { filters -> filters != null && viewModel.filters.hashCode() != filters.hashCode() }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            hasFiltersUpdated,
        ) { (notLoading, notScrolled, hasFiltersUpdated) ->
            notLoading && (notScrolled || hasFiltersUpdated)
        }
            .distinctUntilChanged()

        viewLifecycleOwner.lifecycleScope.launch {
            pagingData.collectLatest(adapter::submitData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) searchResult.rv.scrollToPosition(0)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect(::loadState)
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