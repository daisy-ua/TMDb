package com.example.tmdb.ui.fragments.movie_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.tmdb.R
import com.example.tmdb.databinding.ElementImageRoundedBinding
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.ui.components.buildTagTextView
import com.example.tmdb.utils.ImageManager
import com.example.tmdb.utils.converters.getCountriesString
import com.example.tmdb.utils.converters.getDuration
import com.example.tmdb.utils.converters.getYear
import com.tmdb.models.Country
import com.tmdb.models.Genre
import com.tmdb.models.movies.MovieDetails
import com.tmdb.repository.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var imageBinding: ElementImageRoundedBinding

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMovieDetails(args.movieId.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        imageBinding = ElementImageRoundedBinding.bind(binding.root)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        viewModel.movieDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Response.Success -> showContent(response.data!!)

                is Response.Error -> {
                    Log.d("daisy", "error")
                }

                is Response.Loading -> {
                    Log.d("daisy", "loading")
                }
            }
        })
    }

    private fun showContent(movie: MovieDetails) {
        setupBasicMovieInfo(movie)
        setupPosters(movie)
        setupTags(movie)
        setupGenres(movie.genres)
        setupCountries(movie.productionCountries)
    }

    private fun setupBasicMovieInfo(movie: MovieDetails) {
        binding.enTitle.text = movie.title

        binding.description.text = movie.overview
    }

    private fun setupPosters(movie: MovieDetails) {
        movie.posterPath?.let {
            ImageManager.getBlurredImage(binding.imageBackground.imageBackground, it)
            ImageManager.getImage(imageBinding.image, it)
        }
    }

    private fun setupTags(movie: MovieDetails) {
        with(binding) {
            mainTags.tag1.icon.setImageResource(R.drawable.ic_calendar)
            mainTags.tag1.name.setText(R.string.year)
            mainTags.tag1.value.text = getYear(movie.releaseDate).toString()

            mainTags.tag2.icon.setImageResource(R.drawable.ic_duration)
            mainTags.tag2.name.setText(R.string.duration)
            mainTags.tag2.value.text = movie.runtime?.let { formattedDuration ->
                getDuration(formattedDuration)
            } ?: this@MovieDetailsFragment.resources.getString(R.string.none_info)

            mainTags.tag3.icon.setImageResource(R.drawable.ic_rating)
            mainTags.tag3.name.setText(R.string.rating)
            mainTags.tag3.value.text = movie.voteAverage.toString()
        }
    }

    private fun setupGenres(genres: List<Genre>) {
        for (genre in genres) {
            this@MovieDetailsFragment.context?.let { context ->
                binding.genreContainer.addView(buildTagTextView(context, genre.name))
            }
        }
    }

    private fun setupCountries(countries: List<Country>) {
        binding.countries.text = getCountriesString(countries)
    }
}
