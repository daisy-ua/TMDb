package com.example.tmdb.ui.fragments.explore.filters

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.tmdb.constants.AppConstants.DEFAULT_QUERY
import com.example.tmdb.constants.FilterKeys
import com.example.tmdb.constants.SortOption
import com.example.tmdb.databinding.FragmentExploreFiltersBinding
import com.example.tmdb.ui.components.buildTagChip
import com.example.tmdb.ui.fragments.explore.ExploreViewModel
import com.example.tmdb.ui.utils.uistate.UiAction
import com.example.tmdb.utils.getFilterMap
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
        viewModel.accept(
            UiAction.Search(
                filters = getFilterMap(
                    binding.sortByContainer.checkedChipId,
                    binding.genreContainer.checkedChipIds
                ),
                query = DEFAULT_QUERY
            )
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
        viewModel.state.value.filters?.get(FilterKeys.WITH_GENRES)?.let { map ->
            (map as List<Int>).forEach { id ->
                binding.genreContainer.check(id)
            }
        }
    }

    private fun checkSelectedSortOption() {
        val validId = viewModel.state.value.filters?.get(FilterKeys.SORT_BY)?.let { id ->
            if (id as Int == -1) 0 else id
        } ?: 0
        binding.sortByContainer.check(validId)
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