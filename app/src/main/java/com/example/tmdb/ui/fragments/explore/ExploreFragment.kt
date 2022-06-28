package com.example.tmdb.ui.fragments.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdb.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private val viewModel: ExploreViewModel by viewModels()

    private val filtersDialog = ExploreFiltersFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backBtn.setOnClickListener { showFiltersDialog() }
    }

    private fun showFiltersDialog() {
        if (!filtersDialog.isVisible) {
            filtersDialog.show(childFragmentManager, ExploreFiltersFragment.TAG)
        }
    }
}