package com.example.tmdb.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdb.R
import com.example.tmdb.databinding.ContentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ContentMainBinding
    private val navController: NavController by lazy {
        findNavController(this, R.id.nav_host_fragment)
    }

    private var isBackPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationView()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {
        when (navController.currentDestination?.id) {
            R.id.explore_fragment -> onExploreFragmentBackPressed()

            R.id.home_fragment -> onExitBackPressed()

            R.id.saved_fragment -> onExploreFragmentBackPressed()

            else -> super.onBackPressed()
        }
    }

    private fun onExploreFragmentBackPressed() {
        binding.bottomNavigationView.selectedItemId = R.id.home_fragment
    }

    private fun onExitBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed()
            return
        }

        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()

        isBackPressedOnce = true

        Handler(Looper.getMainLooper()).postDelayed({
            isBackPressedOnce = false
        }, 2000)
    }

    private fun setupBottomNavigationView() =
        binding.bottomNavigationView.setupWithNavController(navController)

    fun showBottomNavigationView() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    val isBottomNavigationViewVisible
        get() = binding.bottomNavigationView.isVisible
}