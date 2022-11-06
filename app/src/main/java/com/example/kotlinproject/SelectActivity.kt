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
            val nextIntent = Intent(this@SelectActivity, HistoryActivity ::class.java)
            startActivity(nextIntent)
        }

        binding.btnStartRandom.setOnClickListener {
            val nextIntent = Intent(this@SelectActivity, ResultActivity ::class.java)
            intent.putExtra("result",R.drawable.select_bibim_bap)    //음식 이름 넣어두기 비빔밥말고 다른거도 가능하도록...
            startActivity(nextIntent)
        }

        binding.btnMap2.setOnClickListener {
            val intent = Intent(this@SelectActivity, MapsActivity::class.java )
            startActivity(intent)
        }

    }
}