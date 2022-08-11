package com.example.tmdb.ui.fragments.seemore

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
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.constants.HomeCategory
import com.example.tmdb.databinding.FragmentSeeMoreBinding
import com.example.tmdb.ui.activity.MainActivity
import com.example.tmdb.ui.components.recyclerview.setupRecyclerView
import com.example.tmdb.ui.utils.adapters.MoviePagingAdapter
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.loadstate.PagingLoadStateAdapter
import com.example.tmdb.ui.utils.rvdecorators.GridItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeeMoreFragment : Fragment(), Interaction {
    private val args: SeeMoreFragmentArgs by navArgs()

    private lateinit var binding: FragmentSeeMoreBinding
    private val viewModel: SeeMoreViewModel by viewModels()

    private val adapter = MoviePagingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMovies(args.category)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).let { mainActivity ->
            if (mainActivity.isBottomNavigationViewVisible) {
                mainActivity.hideBottomNavigationView()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        (requireActivity() as MainActivity).showBottomNavigationView()
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
                    viewModel.movies.collectLatest(adapter::submitData)
                }

                launch {
                    adapter.loadStateFlow.collect(::loadState)
                }
            }
        }
    }

    private fun loadState(loadState: CombinedLoadStates) {
        binding.movieRecycleView.isVisible =
            loadState.source.refresh is LoadState.NotLoading

        binding.progressBar.isVisible =
            loadState.source.refresh is LoadState.Loading
    }

    private fun setTitle() {
        when (args.category) {
            HomeCategory.NOW_PLAYING -> R.string.now_playing

            HomeCategory.TRENDING -> R.string.trending

            HomeCategory.TOP_RATED -> R.string.top_rated
        }.also { id ->
            binding.toolbarTitle.text = resources.getString(id)
        }
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