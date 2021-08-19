package com.example.tmdb.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb.data.models.Movie
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.ui.adapters.MovieAdapter
import com.example.tmdb.utils.Resource
import com.example.tmdb.utils.getMainViewModel
import com.example.tmdb.viewmodels.MainViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by lazy { getMainViewModel(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        setupListeners()
        return binding.root
    }

    private fun setupRecyclerView() = with(binding.movieList.rv) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        adapter = MovieAdapter()
    }

    private fun setupListeners() {
        viewModel.popularMoviesResponse.observe(viewLifecycleOwner, listObserver)
    }

    private val listObserver = Observer<Resource<List<Movie>>> { response ->
        with(binding) {
            progressBar.isVisible = response is Resource.Loading && response.data.isNullOrEmpty()
            errorMsg.isVisible = response is Resource.Error && response.data.isNullOrEmpty()
            errorMsg.text = response.throwable?.localizedMessage

            (movieList.rv.adapter as MovieAdapter).run {
                submitList(response.data)
                notifyDataSetChanged()
            }
        }
    }
}