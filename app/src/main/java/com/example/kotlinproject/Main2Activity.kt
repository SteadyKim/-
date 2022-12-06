package com.example.kotlinproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlinproject.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMain2Binding.inflate(layoutInflater)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        binding.bottomNav.setupWithNavController(navController)
        setContentView(binding.root)



    }

}