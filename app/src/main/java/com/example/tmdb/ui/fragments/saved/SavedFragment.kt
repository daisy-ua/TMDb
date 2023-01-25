package com.example.tmdb.ui.fragments.saved

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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentSeeMoreBinding
import com.example.tmdb.ui.components.recyclerview.setupRecyclerView
import com.example.tmdb.ui.fragments.seemore.SeeMoreFragmentDirections
import com.example.tmdb.ui.utils.adapters.MoviePagingAdapter
import com.example.tmdb.ui.utils.eventmanager.EventManager
import com.example.tmdb.ui.utils.eventmanager.events.BaseEvents
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.loadstate.PagingLoadStateAdapter
import com.example.tmdb.ui.utils.rvdecorators.GridItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : Fragment(), Interaction {
    private lateinit var binding: FragmentSeeMoreBinding

    private val viewModel: SavedViewModel by viewModels()
    private val eventManager: EventManager by viewModels({ requireActivity() })

    private val adapter = MoviePagingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventManager.shouldApplyModifications = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSeeMoreBinding.inflate(layoutInflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        setTitle()

        setupListView(adapter.withLoadStateFooter(PagingLoadStateAdapter(adapter::retry)))

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movies
                        .combine(eventManager.modificationEvents) { pagingData, modifications ->
                            modifications.fold(pagingData) { acc, event ->
                                if (event is BaseEvents.Remove && adapter.itemCount == 1) {
                                    binding.msg.isVisible = true
                                }
                                viewModel.applyEvents(acc, event)
                            }
                        }
                        .distinctUntilChanged()
                        .collectLatest(adapter::submitData)
                }

                launch {
                    adapter.loadStateFlow.collect(::loadState)
                }
            }
        }
    }

    private fun loadState(loadState: CombinedLoadStates) {
        binding.msg.isVisible =
            (loadState.refresh is LoadState.NotLoading
                    && (adapter.itemCount) < 1)

        binding.progressBar.isVisible =
            loadState.source.refresh is LoadState.Loading

        binding.movieRecycleView.isVisible =
            loadState.source.refresh is LoadState.NotLoading
    }

    private fun setTitle() {
        binding.toolbarTitle.text = resources.getString(R.string.saved_name)
    }

    private fun setupListView(pagingAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        val itemDecoration =
            GridItemDecorator(3, resources.getDimension(R.dimen.rv_item_margin_small))

        setupRecyclerView(
            binding.movieRecycleView,
            context,
            pagingAdapter,
            GridLayoutManager(
                context,
                3
            ),
            false,
            itemDecoration = itemDecoration
        )
    }

    override fun onItemClicked(id: Long) {
        val action = SeeMoreFragmentDirections.getMovieDetailsAction(id)
        findNavController().navigate(action)
    }
}