package com.example.kotlinproject

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinproject.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMain2Binding.inflate(layoutInflater)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
//        val appBarConfiguration = AppBarConfiguration (
//            setOf(R.id.entryFragment, R.id.historyFragment, R.id.mapFragment, R.id.selectFragment)
//                )
        binding.bottomNav.setupWithNavController(navController )
        setContentView(binding.root)

        val animationDrawable = binding.ctrMain.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(1000)
        animationDrawable.setExitFadeDuration(1000)
        animationDrawable.start()
    }
}