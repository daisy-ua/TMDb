package com.example.tmdb.ui.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tmdb.R
import com.example.tmdb.data.models.Movie

class MovieDetailsFragment : Fragment() {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        movie = args.movie
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }
}