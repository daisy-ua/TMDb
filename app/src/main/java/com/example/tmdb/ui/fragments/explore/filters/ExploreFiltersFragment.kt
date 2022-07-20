package com.example.tmdb.ui.fragments.explore.filters

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tmdb.constants.FilterKeys
import com.example.tmdb.constants.SortOption
import com.example.tmdb.databinding.FragmentExploreFiltersBinding
import com.example.tmdb.ui.components.buildTagChip
import com.example.tmdb.ui.fragments.explore.ExploreViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tmdb.models.Genre
import com.tmdb.repository.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFiltersFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentExploreFiltersBinding
    private val viewModel: ExploreViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExploreFiltersBinding.inflate(inflater, container, false)
        setupListeners()
        setupSortOptions()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkSelectedSortOption()
    }

    override fun onCancel(dialog: DialogInterface) {
        binding.sortByContainer.clearCheck()
    }

    private fun setupListeners() {
        viewModel.genres.observe(viewLifecycleOwner, genreLoadingObserver)

        binding.applyFilters.setOnClickListener(onApplyFiltersClickListener)
    }

    private val onApplyFiltersClickListener = View.OnClickListener {
        applyFilters()
        this@ExploreFiltersFragment.dismiss()
    }

    private fun applyFilters() {
        viewModel.setFilters(
            binding.sortByContainer.checkedChipId,
            binding.genreContainer.checkedChipIds
        )
    }

    private val genreLoadingObserver = Observer<Response<List<Genre>>?> { response ->
        if (response is Response.Success) {
            setupGenres(response.data!!)
            checkSelectedGenres()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun checkSelectedGenres() {
        viewModel.filters.value?.get(FilterKeys.WITH_GENRES)?.let { map ->
            (map as List<Int>).forEach { id ->
                binding.genreContainer.check(id)
            }
        }
    }

    private fun checkSelectedSortOption() {
        viewModel.filters.value?.get(FilterKeys.SORT_BY).let { id ->
            binding.sortByContainer.check(id as Int)
        }
    }

    private fun setupGenres(genres: List<Genre>) {
        for (genre in genres) {
            buildTagChip(binding.genreContainer, genre.name, genre.id)
        }
    }

    private fun setupSortOptions() {
        for ((index, option) in SortOption.values().withIndex()) {
            buildTagChip(binding.sortByContainer, option.optionName, index)
        }
    }

    companion object {
        const val TAG = "ExploreFiltersFragment"
    }
}