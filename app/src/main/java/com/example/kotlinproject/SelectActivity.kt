package com.example.kotlinproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.databinding.ActivitySelectBinding

class SelectActivity : AppCompatActivity() {


    //2. 레이아웃(root뷰) 표시
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySelectBinding.inflate(layoutInflater);

        setContentView(binding.root)


        binding.btnHistory.setOnClickListener {
            val nextIntent = Intent(this@SelectActivity, HistoryActivity::class.java)
            startActivity(nextIntent)
        }

    }
}