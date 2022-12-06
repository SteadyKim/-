package com.example.kotlinproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinproject.databinding.ActivityMain2Binding
import com.example.kotlinproject.utils.AlarmReceiver

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMain2Binding.inflate(layoutInflater)
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        binding.bottomNav.setupWithNavController(navController)
        setContentView(binding.root)


    }

}