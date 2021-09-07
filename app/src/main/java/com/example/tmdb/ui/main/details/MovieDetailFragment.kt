package com.example.tmdb.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.example.tmdb.utils.components.buildTagTextView
import com.example.tmdb.utils.network.Resource
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

    private var year: Int? = null
    private var duration: String? = null

    init {
        lifecycleScope.launch {
            whenCreated {
                viewModel = getViewModel(this@MovieDetailFragment, MovieDetailViewModelFactory())
                genreViewModel = getViewModel(this@MovieDetailFragment, GenreViewModelFactory())
                viewModel.setMovieDetails(args.movieId.toInt())
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
        viewModel.movieResponse.observeOnce(viewLifecycleOwner, Observer(::setupMainScreenContent))
    }

    private fun setupMainScreenContent(response: Resource<Movie>) {
        when(response) {
            is Resource.Success -> displaySuccessContent(response.data!!)
            is Resource.Error -> {}
            is Resource.Loading -> {}
        }
    }

    private fun displaySuccessContent(data: Movie) {
        loadInitialData(data)
        showContent()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = data.title
    }

    private fun loadInitialData(movie: Movie) {
        year = getYear(movie.releaseDate)
        duration = getDuration(movie.runtime ?: 0)
        genreViewModel.setGenreNames(*movie.genre.toIntArray())
        lifecycleScope.launch {
            viewModel.setSimilarMovies(movie.id)
        }
    }

    private fun showContent() {
        viewModel.movieResponse.value?.data?.let { movie ->
            with(binding) {
                movieTitle.text = movie.title
                movieDescription.text = movie.overview
                movieDuration.text = duration
                movieYear.text = year.toString()
                ImageManager.getBlurredImage(imageBackground.imageBackground, movie.posterPath)
                ImageManager.getImage(imageBinding.image, movie.posterPath)
            }
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

        genreViewModel.genres.observeOnce(viewLifecycleOwner, Observer(::setupGenreLayout))
    }

    private fun setupGenreLayout(data: List<Genre>) { // TODO("add on click redirection")
        for (genre in data) {
            binding.movieGenres.addView(buildTagTextView(requireContext(), genre.name))
        }
    }

    override fun onItemClicked(position: Int) {
        TODO("Not yet implemented")
    }
}