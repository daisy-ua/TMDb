package com.example.tmdb.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appBarConfiguration()
        setupBottomNavigationView()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    private fun setupBottomNavigationView() =
        binding.bottomNavigationView.setupWithNavController(navController)

    private fun appBarConfiguration() {
        val config = AppBarConfiguration(setOf(R.id.home_fragment, R.id.explore_fragment))
        setupActionBarWithNavController(navController, config)
    }
}
