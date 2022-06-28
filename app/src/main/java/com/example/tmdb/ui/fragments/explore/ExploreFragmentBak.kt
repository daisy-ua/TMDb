package com.example.tmdb.ui.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tmdb.databinding.FragmentExploreBinding

class ExploreFragmentBak : DialogFragment() {
    private lateinit var binding: FragmentExploreBinding
//    private val viewModel: ExploreViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
//        setupListeners()
//        setupSortOptions()
//        Log.d("daisy", viewModel.filters?.withGenres.toString())
        return binding.root
    }

//    private fun setupListeners() {
//        viewModel.genres.observe(viewLifecycleOwner, genreObserver)
//
//        binding.searchBar.setOnClickListener {
//
//            val filters = saveFilters()
//            viewModel.filters = filters
//            navigateToSearchResults()
//        }
//    }
//
//    private val genreObserver = Observer<Response<List<Genre>>?> { response ->
//        if (response is Response.Success) setupGenres(response.data!!)
//    }
//
//    private fun setupGenres(genres: List<Genre>) {
//        for (genre in genres) {
//            buildTagChip(binding.genreContainer, genre.name, genre.id)
//        }
//    }
//
//    private fun setupSortOptions() {
//        for (option in SortOption.values()) {
//            buildTagChip(binding.sortByContainer, option.optionName)
//        }
//    }
//
//    private fun saveFilters() = DiscoverFilters.Builder().let { builder ->
//        binding.genreContainer.checkedChipIds.let { genres ->
//            if (genres.isNotEmpty())
//                builder.withGenres(genres)
//        }
//
//        binding.sortByContainer.checkedChipId.let { position ->
//            if (position > 0)
//                builder.sortBy(SortOption.values()[position - 1].getCode())
//        }
//
//        builder.build()
//    }
//
//    private fun navigateToSearchResults() {
//        val action = ExploreFragmentDirections.getExploreFiltersAction()
//        findNavController().navigate(action)
//    }
}
