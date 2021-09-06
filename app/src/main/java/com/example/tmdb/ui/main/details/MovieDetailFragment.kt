package com.example.tmdb.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb.data.models.Genre
import com.example.tmdb.data.models.Movie
import com.example.tmdb.databinding.ElementImageRoundedBinding
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.ui.adapters.MovieAdapter
import com.example.tmdb.ui.interactions.Interaction
import com.example.tmdb.utils.extensions.observeOnce
import com.example.tmdb.utils.components.getRecyclerViewDataSetupObserver
import com.example.tmdb.utils.network.ImageManager
import com.example.tmdb.utils.components.setupRecyclerView
import com.example.tmdb.utils.components.getTagTextView
import com.example.tmdb.utils.ui_converters.getDuration
import com.example.tmdb.utils.ui_converters.getYear
import com.example.tmdb.viewmodels.*
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(), Interaction {
    private val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var imageBinding: ElementImageRoundedBinding

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var genreViewModel: GenreViewModel

    private lateinit var movie: Movie
    private var year: Int? = null
    private var duration: String? = null

    init {
        lifecycleScope.launch {
            whenCreated {
                viewModel = getViewModel(this@MovieDetailFragment, MovieDetailViewModelFactory())
                genreViewModel = getViewModel(this@MovieDetailFragment, GenreViewModelFactory())

                movie = args.movie
                year = getYear(movie.releaseDate)
                duration = getDuration(movie.runtime ?: 0)
                genreViewModel.setGenreNames(*movie.genre.toIntArray())
                viewModel.setSimilarMovies(movie.id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        imageBinding = ElementImageRoundedBinding.bind(binding.root)
        setupView()
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            movieTitle.text = movie.title
            movieDescription.text = movie.overview
            movieDuration.text = duration
            movieYear.text = year.toString()
            ImageManager.getBlurredImage(imageBackground.imageBackground, movie.posterPath)
            ImageManager.getImage(imageBinding.image, movie.posterPath)
        }
    }

    private fun setupView() {
        setupRecyclerView(
            binding.similarMovies.rv,
            requireContext(),
            MovieAdapter(this),
            layout = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        )
    }

    private fun setupListeners() {
        viewModel.similarMovies.observe(
            viewLifecycleOwner,
            getRecyclerViewDataSetupObserver(binding.similarMovies)
        )

        genreViewModel.genres.observeOnce(
            viewLifecycleOwner,
            genreLayoutObserver(binding.movieGenres)
        )
    }

    override fun onItemClicked(position: Int) {
        TODO("Not yet implemented")
    }

    private fun genreLayoutObserver(layout: ViewGroup) = Observer<List<Genre>> { data ->
        for (genre in data) {
            layout.addView(getTagTextView(requireContext(), genre.name))
        }
//        TODO("add multiline integration")
//        TODO("add on click redirection")
    }
}