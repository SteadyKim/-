package com.example.kotlinproject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinproject.databinding.ActivityMainBinding

class   MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 바인딩 초기화
        val binding = ActivityMainBinding.inflate(layoutInflater);

        //2. 레이아웃(root뷰) 표시
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            val nextIntent = Intent(this@MainActivity, SelectActivity::class.java)
            startActivity(nextIntent)
        }
        //UI
//        val constraintLayout: LinearLayout = findViewById(R.id.mainLayout)
//        val animationDrawable: AnimationDrawable = constraintLayout.background as AnimationDrawable
//        animationDrawable.setEnterFadeDuration(1500)
//        animationDrawable.setExitFadeDuration(2000)
//        animationDrawable.start()
    }


}