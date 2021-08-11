package com.example.tmdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tmdb.utils.getMainViewModel
import com.example.tmdb.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy { getMainViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewModel.getMovieDetails(436969, BuildConfig.API_KEY)
//        viewModel.getSimilarMovies(436969, BuildConfig.API_KEY)
//        viewModel.discoverMovies("80, 18", BuildConfig.API_KEY)
//        viewModel.getPopularMovies(BuildConfig.API_KEY)
        viewModel.getGenres(BuildConfig.API_KEY)
    }
}