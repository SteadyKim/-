package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater);

        if (intent.hasExtra("result")) {
            binding.txtResult.text =
                intent.getStringExtra(("result"))  //ResultActivity의 텍스트 뷰에 음식 이름 넘겨져 옴
        }
        binding.btnRedo.setOnClickListener {
            binding.imgResult.setImageResource(R.drawable.select_buchim_gae)    //이미지 뷰에 새로운 이미지 등록..다른 것도 가능하도록!하기
        }

       val  clickBtnAnything: Button = findViewById(R.id.btn_anything)
        clickBtnAnything.setOnClickListener{
            randomFood()
        }

    }


        fun  randomFood() {
            val randomNumber = (1..36).random()

            val randomResource = when(randomNumber){
                1 -> R.drawable.select_bibim_bap
                2 -> R.drawable.select_buchim_gae
                3 -> R.drawable.select_cheong_guk_jang
                4 -> R.drawable.select_chicken
                5 -> R.drawable.select_dak_bal
                6 -> R.drawable.select_ddek_bokki
                7 -> R.drawable.select_do_si_rak
                8 -> R.drawable.select_doen_jang_jjigae
                9 -> R.drawable.select_don_gas
                10 -> R.drawable.select_galbitang

                11 -> R.drawable.select_gim_bap
                12 -> R.drawable.select_gob_chang
                13 -> R.drawable.select_gug_bab
                14 -> R.drawable.select_haejang_gug
                15 -> R.drawable.main_hamburger
                16 -> R.drawable.select_hotdog
                17 -> R.drawable.select_jajjang
                18 -> R.drawable.select_jeyugbokk_eum
                19 -> R.drawable.select_jjambong
                20 -> R.drawable.select_jukkumi

                21 -> R.drawable.select_kalgugsu
                22 -> R.drawable.select_man_do
                23 -> R.drawable.select_mara_tang
                24 -> R.drawable.select_omurice
                25 -> R.drawable.select_pasta
                26 -> R.drawable.select_pizza
                27 -> R.drawable.select_ramen
                28 -> R.drawable.select_sam_gye_tang
                29 -> R.drawable.select_sam_gyeob_sal
                30 -> R.drawable.select_sandwich

                31 -> R.drawable.select_sashimi
                32 -> R.drawable.select_soyed_crab
                33 -> R.drawable.select_ssal_guksu
                34 -> R.drawable.select_sundaebokk_eum
                35 -> R.drawable.select_yang_kko_chi
                else -> R.drawable.select_yug_hoe
            }
            val resultImage : ImageView = findViewById(R.id.img_result)
            resultImage.setImageResource(randomResource)
        }

    }
