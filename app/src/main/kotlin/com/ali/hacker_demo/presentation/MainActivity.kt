package com.ali.hacker_demo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ali.hacker_demo.R
import com.ali.hacker_demo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mMainViewModel: MainViewModel by viewModels()
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainViewModel.getTopStories()
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupNavController()

    }

    private fun setupNavController() {
        with(mBinding) {
            val navHost = supportFragmentManager.findFragmentById(R.id.mainFcv) as NavHostFragment
            val appBarConfiguration = AppBarConfiguration(navHost.navController.graph)
            toolbar
                .setupWithNavController(navHost.navController, appBarConfiguration)
        }
    }
}