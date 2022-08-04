package com.example.tmdb.ui.fragments.movie_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.tmdb.R
import com.example.tmdb.constants.AppConstants.YT_SITE_NAME
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.ui.components.buildTagTextView
import com.example.tmdb.utils.ImageManager
import com.example.tmdb.utils.converters.getDuration
import com.example.tmdb.utils.converters.getYear
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.tmdb.models.Country
import com.tmdb.models.Genre
import com.tmdb.models.Video
import com.tmdb.models.movies.MovieDetails
import com.tmdb.repository.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()

    private var ytTracker: YouTubePlayerTracker = YouTubePlayerTracker()

    private var playWhenReady = false
    private var playbackPosition = 0f
    private var videoKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMovieDetails(args.movieId.toInt())
        viewModel.fetchMovieVideos(args.movieId.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        playbackPosition =
            if (ytTracker.state == PlayerConstants.PlayerState.ENDED) 0f
            else ytTracker.currentSecond

        playWhenReady = ytTracker.state == PlayerConstants.PlayerState.PLAYING
    }

    private fun setupListeners() {
        viewLifecycleOwner.lifecycle.addObserver(binding.scrollContent.trailerView)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movieDetails.collect { uiState ->
                        when (uiState) {
                            is Response.Success -> showContent(uiState.data!!)

                            is Response.Error -> Log.d("daisy", "error details")

                            else -> {}
                        }
                    }
                }

                launch {
                    viewModel.videos.collect { uiState ->
                        when (uiState) {
                            is Response.Success -> showMovieVideos(uiState.data!!)

                            is Response.Error -> Log.d("daisy", "error")

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun showMovieVideos(videos: List<Video>) {
        videos.filter { it.site == YT_SITE_NAME }.also { ytVideos ->
            videoKey = ytVideos[0].key

            binding.scrollContent.trailerView.getYouTubePlayerWhenReady(object :
                YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.addListener(ytTracker)

                    if (playWhenReady) {
                        youTubePlayer.loadVideo(videoKey!!, playbackPosition)
                    } else {
                        youTubePlayer.cueVideo(videoKey!!, playbackPosition)
                    }
                }
            })
        }
    }

    private fun showContent(movie: MovieDetails) {
        setupBasicMovieInfo(movie)
        setupPosters(movie)
        setupTags(movie)
        setupGenres(movie.genres)
        setupCountries(movie.productionCountries)
    }

    private fun setupBasicMovieInfo(movie: MovieDetails) {
        binding.scrollContent.enTitle.text = movie.title

        binding.scrollContent.description.text = movie.overview
    }

    private fun setupPosters(movie: MovieDetails) {
        movie.posterPath?.let {
            ImageManager.getBlurredImage(binding.imageBackground.imageBackground, it)
            ImageManager.getImage(binding.scrollContent.posterContainer, it)
        }
    }

    private fun setupTags(movie: MovieDetails) {
        with(binding.scrollContent) {
            mainTags.tag1.icon.setImageResource(R.drawable.ic_calendar)
            mainTags.tag1.name.setText(R.string.year)
            mainTags.tag1.value.text = getYear(movie.releaseDate).toString()

            mainTags.tag2.icon.setImageResource(R.drawable.ic_duration)
            mainTags.tag2.name.setText(R.string.duration)
            mainTags.tag2.value.text = movie.runtime?.let { formattedDuration ->
                getDuration(formattedDuration)
            } ?: this@MovieDetailsFragment.resources.getString(R.string.none_info)

            mainTags.tag3.icon.setImageResource(R.drawable.ic_star_24)
            mainTags.tag3.name.setText(R.string.rating)
            mainTags.tag3.value.text = String.format("%.1f", movie.voteAverage)
        }
    }

    private fun setupGenres(genres: List<Genre>) {
        binding.scrollContent.genreContainer.let { container ->
            container.removeAllViews()
            for (genre in genres) {
                container.addView(buildTagTextView(requireContext(), genre.name))
            }
        }

    }

    private fun setupCountries(countries: List<Country>) {
        binding.scrollContent.countries.text =
            countries.joinToString { country -> country.name }
    }
}
