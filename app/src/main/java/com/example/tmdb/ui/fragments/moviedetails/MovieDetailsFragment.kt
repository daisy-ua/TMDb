package com.example.tmdb.ui.fragments.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.daisy.constants.Response
import com.daisy.domain.models.Country
import com.daisy.domain.models.Genre
import com.daisy.domain.models.Video
import com.daisy.domain.models.movies.Movie
import com.daisy.domain.models.movies.MovieDetails
import com.example.tmdb.R
import com.example.tmdb.constants.AppConstants.YT_SITE_NAME
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.ui.activity.MainActivity
import com.example.tmdb.ui.components.buildTagTextView
import com.example.tmdb.ui.components.recyclerview.setupRecyclerView
import com.example.tmdb.ui.utils.adapters.MovieAdapter
import com.example.tmdb.ui.utils.eventmanager.EventManager
import com.example.tmdb.ui.utils.eventmanager.events.BaseEvents
import com.example.tmdb.ui.utils.interactions.Interaction
import com.example.tmdb.ui.utils.rvdecorators.LinearItemDecoration
import com.example.tmdb.utils.ImageManager
import com.example.tmdb.utils.converters.getDuration
import com.example.tmdb.utils.converters.getYear
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), Interaction {
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val eventManager: EventManager by viewModels({ requireActivity() })

    private var ytTracker: YouTubePlayerTracker = YouTubePlayerTracker()

    private var playWhenReady = false
    private var playbackPosition = 0f
    private var videoKey: String? = null

    private lateinit var movieRecommendationAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMovieDetails(args.movieId.toInt())
        viewModel.fetchMovieVideos(args.movieId.toInt())
        viewModel.fetchMovieRecommendations(args.movieId.toInt())
        viewModel.isMovieSaved(args.movieId.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        setupRecyclerView(binding.scrollContent.recommendedMovies.rv)
        setupListeners()
        return binding.root
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

    override fun onPause() {
        super.onPause()

        playbackPosition =
            if (ytTracker.state == PlayerConstants.PlayerState.ENDED) 0f
            else ytTracker.currentSecond

        playWhenReady = ytTracker.state == PlayerConstants.PlayerState.PLAYING
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveBtn.setOnClickListener {
            viewModel.updateIsMovieSaved(args.movieId.toInt())
            if (eventManager.shouldApplyModifications) {
                eventManager.emitEvent(
                    if (viewModel.isSaved.value.data == true) {
                        BaseEvents.Remove(args.movieId.toInt())
                    } else {
                        BaseEvents.InsertItemHeader(getDataInstance())
                    }
                )
            }
        }

        viewLifecycleOwner.lifecycle.addObserver(binding.scrollContent.trailerView)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movieDetails.collect { uiState ->
                        when (uiState) {
                            is Response.Success -> showContent(uiState.data!!)

                            is Response.Error -> showError(uiState.throwable?.message.toString())

                            else -> showLoading()
                        }
                    }
                }

                launch {
                    viewModel.videos.collect { uiState ->
                        when (uiState) {
                            is Response.Success -> showMovieVideos(uiState.data!!)

                            is Response.Error -> {}

                            else -> {}
                        }
                    }
                }

                launch {
                    viewModel.movieRecommendations.collect { uiState ->
                        when (uiState) {
                            is Response.Success -> showMovieRecommendations(uiState.data?.movies)

                            is Response.Error -> hideMovieRecommendations()

                            else -> {}
                        }
                    }
                }

                launch {
                    viewModel.isSaved.collect { uiState ->
                        val iconId = if (uiState is Response.Success && uiState.data == true) {
                            R.drawable.ic_save_filled
                        } else {
                            R.drawable.ic_save_stroked
                        }
                        binding.saveBtn.background =
                            ResourcesCompat.getDrawable(resources, iconId, null)
                    }
                }
            }
        }
    }

    private fun getDataInstance() = viewModel.movieDetails.value?.data!!.let {
        Movie(
            posterPath = it.posterPath,
            adult = it.adult,
            overview = it.overview,
            releaseDate = it.releaseDate,
            genreIds = listOf(),
            id = it.id,
            originalTitle = it.originalTitle,
            originalLanguage = it.originalLanguage,
            title = it.title,
            backdropPath = it.backdropPath,
            popularity = it.popularity,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            video = it.video
        )
    }

    private fun showLoading() {
        binding.mainProgressBar.isVisible = true
        binding.scrollContent.root.isVisible = false
    }

    private fun showError(message: String) {
        binding.mainProgressBar.isVisible = false
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showMovieRecommendations(movies: List<Movie>?) {
        if (movies.isNullOrEmpty()) {
            hideMovieRecommendations()
        } else {
            movieRecommendationAdapter.submitList(movies)
        }
    }

    private fun hideMovieRecommendations() {
        binding.scrollContent.recommendedMoviesName.isVisible = false
    }

    private fun showMovieVideos(videos: List<Video>) {
        videos.filter { it.site == YT_SITE_NAME }.also { ytVideos ->
            if (ytVideos.isNotEmpty()) {
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
    }

    private fun showContent(movie: MovieDetails) {
        setupBasicMovieInfo(movie)
        setupPosters(movie)
        setupTags(movie)
        movie.genres?.let { setupGenres(it) }
        movie.productionCountries?.let { setupCountries(it) }

        binding.mainProgressBar.isVisible = false
        binding.scrollContent.root.isVisible = true
        binding.toolbar.isVisible = true
    }

    private fun setupBasicMovieInfo(movie: MovieDetails) {
        binding.scrollContent.enTitle.text = movie.title

        binding.scrollContent.description.text = movie.overview
    }

    private fun setupPosters(movie: MovieDetails) {
        movie.posterPath.let {
            ImageManager.getBlurredImage(binding.imageBackground.imageBackground, it)
            ImageManager.getImage(binding.scrollContent.posterContainer, it)
        }
    }

    private fun setupTags(movie: MovieDetails) {
        with(binding.scrollContent) {
            mainTags.tag1.icon.setImageResource(R.drawable.ic_calendar)
            mainTags.tag1.name.setText(R.string.year)
            movie.releaseDate?.let { mainTags.tag1.value.text = getYear(it).toString() }

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

    private fun setupRecyclerView(rv: RecyclerView) {
        movieRecommendationAdapter = MovieAdapter(this)

        val itemDecoration =
            LinearItemDecoration(resources.getDimension(R.dimen.rv_item_margin_small))

        setupRecyclerView(
            rv,
            context,
            adapter = movieRecommendationAdapter,
            hasFixedSize = true,
            itemDecoration = itemDecoration
        )
    }

    override fun onItemClicked(id: Long) {
        val action = MovieDetailsFragmentDirections.getMovieDetailsAction(id)
        findNavController().navigate(action)
    }
}