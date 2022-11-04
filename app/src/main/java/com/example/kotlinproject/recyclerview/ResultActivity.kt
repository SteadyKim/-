package com.example.kotlinproject.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater);

        if (intent.hasExtra("result")){
            binding.txtResult.text = intent.getStringExtra(("result"))  //ResultActivity의 텍스트 뷰에 음식 이름 넘겨져 옴
        }
        binding.btnRedo.setOnClickListener {
            binding.imgResult.setImageResource(R.drawable.select_buchim_gae)    //이미지 뷰에 새로운 이미지 등록..다른 것도 가능하도록!하기
        }

    }
}