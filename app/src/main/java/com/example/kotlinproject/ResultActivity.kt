package com.example.kotlinproject

//TODO bundle getPutEn()
//TODO arrayList
// do

import android.R.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater);

        setContentView(binding.root)

        binding.btnRedo.setOnClickListener {
            randomFood()
        }
        val anything = intent.getStringArrayListExtra("anything")
        val korean = intent.getStringArrayListExtra("korean")
        val chinese = intent.getStringArrayListExtra("chinese")
        val western = intent.getStringArrayListExtra("western")
        val asian = intent.getStringArrayListExtra("asian")
        val japanese = intent.getStringArrayListExtra("japanese")
        val noodle = intent.getStringArrayListExtra("noodle")
        val meat = intent.getStringArrayListExtra("meat")
        val rice = intent.getStringArrayListExtra("rice")

        if (intent.hasExtra("anything")) {
            if (anything != null) {
                var randomNumber =
                    (1..anything.size).random()
                var randomResource = when (randomNumber) {
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
                    15 -> R.drawable.select_hamburger
                    16 -> R.drawable.select_hotdog
                    17 -> R.drawable.select_jajjang
                    18 -> R.drawable.select_jjambong
                    19 -> R.drawable.select_jukkumi

                    20 -> R.drawable.select_kalgugsu
                    21 -> R.drawable.select_man_do
                    22 -> R.drawable.select_mara_tang
                    23 -> R.drawable.select_omurice
                    24 -> R.drawable.select_pasta
                    25 -> R.drawable.select_pizza
                    26 -> R.drawable.select_ramen
                    27 -> R.drawable.select_sam_gye_tang
                    28 -> R.drawable.select_sam_gyeob_sal
                    29 -> R.drawable.select_sandwich

                    30 -> R.drawable.select_sashimi
                    31 -> R.drawable.select_soyed_crab
                    32 -> R.drawable.select_ssal_guksu

                    33 -> R.drawable.select_sundaebokk_eum
                    34 -> R.drawable.select_yang_kko_chi
                    35 -> R.drawable.select_yug_hoe
                    else -> R.drawable.select_jeyugbokk_eum
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }

        if (intent.hasExtra("korean")) {
            if (korean != null) {
                var randomNumber =
                    (1..korean.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_bibim_bap
                    2 -> R.drawable.select_buchim_gae
                    3 -> R.drawable.select_cheong_guk_jang
                    4 -> R.drawable.select_dak_bal
                    5 -> R.drawable.select_ddek_bokki
                    6 -> R.drawable.select_do_si_rak
                    7 -> R.drawable.select_doen_jang_jjigae
                    8 -> R.drawable.select_galbitang
                    9 -> R.drawable.select_gim_bap
                    10 -> R.drawable.select_gob_chang

                    11 -> R.drawable.select_gug_bab
                    12 -> R.drawable.select_haejang_gug
                    13 -> R.drawable.select_jeyugbokk_eum
                    14 -> R.drawable.select_jukkumi
                    15 -> R.drawable.select_kalgugsu
                    16 -> R.drawable.select_sam_gye_tang
                    17 -> R.drawable.select_galbitang
                    18 -> R.drawable.select_sam_gyeob_sal
                    else -> R.drawable.select_sundaebokk_eum
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("chinese")) {
            if (chinese != null) {
                var randomNumber =
                    (1..chinese.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_gob_chang
                    2 -> R.drawable.select_jajjang
                    3 -> R.drawable.select_jjambong
                    4 -> R.drawable.select_man_do
                    5 -> R.drawable.select_mara_tang
                    else -> R.drawable.select_yang_kko_chi
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }

        if (intent.hasExtra("western")) {
            if (western != null) {
                var randomNumber =
                    (1..western.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_don_gas
                    2 -> R.drawable.select_hamburger
                    3 -> R.drawable.select_hotdog
                    4 -> R.drawable.select_omurice
                    5 -> R.drawable.select_pasta
                    6 -> R.drawable.select_pizza
                    else -> R.drawable.select_sandwich

                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+--+-+-+-+-+-+-+-+
        if (intent.hasExtra("asian")) {
            if (asian != null) {
                var randomNumber =
                    (1..asian.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_bibim_bap
                    2 -> R.drawable.select_buchim_gae
                    3 -> R.drawable.select_chicken
                    4 -> R.drawable.select_dak_bal
                    5 -> R.drawable.select_do_si_rak
                    6 -> R.drawable.select_gim_bap
                    7 -> R.drawable.select_gob_chang
                    8 -> R.drawable.select_jajjang
                    9 -> R.drawable.select_jukkumi
                    10 -> R.drawable.select_kalgugsu

                    11 -> R.drawable.select_man_do
                    12 -> R.drawable.select_mara_tang
                    13 -> R.drawable.select_ramen
                    14 -> R.drawable.select_sam_gyeob_sal
                    15 -> R.drawable.select_soyed_crab
                    16 -> R.drawable.select_ssal_guksu
                    17 -> R.drawable.select_jjambong
                    else -> R.drawable.select_yang_kko_chi
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("japanese")) {
            if (japanese != null) {
                var randomNumber =
                    (1..japanese.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_don_gas
                    2 -> R.drawable.select_ramen
                    3 -> R.drawable.select_sashimi
                    else -> R.drawable.select_soyed_crab
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("noodle")) {
            if (noodle != null) {
                var randomNumber =
                    (1..noodle.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_jajjang
                    2 -> R.drawable.select_jjambong
                    3 -> R.drawable.select_kalgugsu
                    4 -> R.drawable.select_mara_tang
                    5 -> R.drawable.select_pasta
                    6 -> R.drawable.select_ramen
                    else -> R.drawable.select_ssal_guksu
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("meat")) {
            if (meat != null) {
                var randomNumber =
                    (1..meat.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_chicken
                    2 -> R.drawable.select_dak_bal
                    3 -> R.drawable.select_jeyugbokk_eum
                    4 -> R.drawable.select_sam_gyeob_sal
                    5 -> R.drawable.select_yang_kko_chi
                    else -> R.drawable.select_yug_hoe
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("rice")) {
            if (rice != null) {
                var randomNumber =
                    (1..rice.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_bibim_bap
                    2 -> R.drawable.select_cheong_guk_jang
                    3 -> R.drawable.select_do_si_rak
                    4 -> R.drawable.select_galbitang
                    5 -> R.drawable.select_gim_bap
                    6 -> R.drawable.select_haejang_gug
                    7 -> R.drawable.select_gug_bab
                    8 -> R.drawable.select_omurice
                    else -> R.drawable.select_sam_gye_tang
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }

    }

    fun randomFood() {
        val anything = intent.getStringArrayListExtra("anything")
        val korean = intent.getStringArrayListExtra("korean")
        val chinese = intent.getStringArrayListExtra("chinese")
        val western = intent.getStringArrayListExtra("western")
        val asian = intent.getStringArrayListExtra("asian")
        val japanese = intent.getStringArrayListExtra("japanese")
        val noodle = intent.getStringArrayListExtra("noodle")
        val meat = intent.getStringArrayListExtra("meat")
        val rice = intent.getStringArrayListExtra("rice")

        if (intent.hasExtra("anything")) {
            if (anything != null) {
                var randomNumber =
                    (1..anything.size).random()
                var randomResource = when (randomNumber) {
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
                    15 -> R.drawable.select_hamburger
                    16 -> R.drawable.select_hotdog
                    17 -> R.drawable.select_jajjang
                    18 -> R.drawable.select_jjambong
                    19 -> R.drawable.select_jukkumi

                    20 -> R.drawable.select_kalgugsu
                    21 -> R.drawable.select_man_do
                    22 -> R.drawable.select_mara_tang
                    23 -> R.drawable.select_omurice
                    24 -> R.drawable.select_pasta
                    25 -> R.drawable.select_pizza
                    26 -> R.drawable.select_ramen
                    27 -> R.drawable.select_sam_gye_tang
                    28 -> R.drawable.select_sam_gyeob_sal
                    29 -> R.drawable.select_sandwich

                    30 -> R.drawable.select_sashimi
                    31 -> R.drawable.select_soyed_crab
                    32 -> R.drawable.select_ssal_guksu

                    33 -> R.drawable.select_sundaebokk_eum
                    34 -> R.drawable.select_yang_kko_chi
                    35 -> R.drawable.select_yug_hoe
                    else -> R.drawable.select_jeyugbokk_eum
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }

        if (intent.hasExtra("korean")) {
            if (korean != null) {
                var randomNumber =
                    (1..korean.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_bibim_bap
                    2 -> R.drawable.select_buchim_gae
                    3 -> R.drawable.select_cheong_guk_jang
                    4 -> R.drawable.select_dak_bal
                    5 -> R.drawable.select_ddek_bokki
                    6 -> R.drawable.select_do_si_rak
                    7 -> R.drawable.select_doen_jang_jjigae
                    8 -> R.drawable.select_galbitang
                    9 -> R.drawable.select_gim_bap
                    10 -> R.drawable.select_gob_chang

                    11 -> R.drawable.select_gug_bab
                    12 -> R.drawable.select_haejang_gug
                    13 -> R.drawable.select_jeyugbokk_eum
                    14 -> R.drawable.select_jukkumi
                    15 -> R.drawable.select_kalgugsu
                    16 -> R.drawable.select_sam_gye_tang
                    17 -> R.drawable.select_galbitang
                    18 -> R.drawable.select_sam_gyeob_sal
                    else -> R.drawable.select_sundaebokk_eum
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("chinese")) {
            if (chinese != null) {
                var randomNumber =
                    (1..chinese.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_gob_chang
                    2 -> R.drawable.select_jajjang
                    3 -> R.drawable.select_jjambong
                    4 -> R.drawable.select_man_do
                    5 -> R.drawable.select_mara_tang
                    else -> R.drawable.select_yang_kko_chi
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }

        if (intent.hasExtra("western")) {
            if (western != null) {
                var randomNumber =
                    (1..western.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_don_gas
                    2 -> R.drawable.select_hamburger
                    3 -> R.drawable.select_hotdog
                    4 -> R.drawable.select_omurice
                    5 -> R.drawable.select_pasta
                    6 -> R.drawable.select_pizza
                    else -> R.drawable.select_sandwich

                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+--+-+-+-+-+-+-+-+
        if (intent.hasExtra("asian")) {
            if (asian != null) {
                var randomNumber =
                    (1..asian.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_bibim_bap
                    2 -> R.drawable.select_buchim_gae
                    3 -> R.drawable.select_chicken
                    4 -> R.drawable.select_dak_bal
                    5 -> R.drawable.select_do_si_rak
                    6 -> R.drawable.select_gim_bap
                    7 -> R.drawable.select_gob_chang
                    8 -> R.drawable.select_jajjang
                    9 -> R.drawable.select_jukkumi
                    10 -> R.drawable.select_kalgugsu

                    11 -> R.drawable.select_man_do
                    12 -> R.drawable.select_mara_tang
                    13 -> R.drawable.select_ramen
                    14 -> R.drawable.select_sam_gyeob_sal
                    15 -> R.drawable.select_soyed_crab
                    16 -> R.drawable.select_ssal_guksu
                    17 -> R.drawable.select_jjambong
                    else -> R.drawable.select_yang_kko_chi
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("japanese")) {
            if (japanese != null) {
                var randomNumber =
                    (1..japanese.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_don_gas
                    2 -> R.drawable.select_ramen
                    3 -> R.drawable.select_sashimi
                    else -> R.drawable.select_soyed_crab
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("noodle")) {
            if (noodle != null) {
                var randomNumber =
                    (1..noodle.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_jajjang
                    2 -> R.drawable.select_jjambong
                    3 -> R.drawable.select_kalgugsu
                    4 -> R.drawable.select_mara_tang
                    5 -> R.drawable.select_pasta
                    6 -> R.drawable.select_ramen
                    else -> R.drawable.select_ssal_guksu
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("meat")) {
            if (meat != null) {
                var randomNumber =
                    (1..meat.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_chicken
                    2 -> R.drawable.select_dak_bal
                    3 -> R.drawable.select_jeyugbokk_eum
                    4 -> R.drawable.select_sam_gyeob_sal
                    5 -> R.drawable.select_yang_kko_chi
                    else -> R.drawable.select_yug_hoe
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("rice")) {
            if (rice != null) {
                var randomNumber =
                    (1..rice.size).random()
                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_bibim_bap
                    2 -> R.drawable.select_cheong_guk_jang
                    3 -> R.drawable.select_do_si_rak
                    4 -> R.drawable.select_galbitang
                    5 -> R.drawable.select_gim_bap
                    6 -> R.drawable.select_haejang_gug
                    7 -> R.drawable.select_gug_bab
                    8 -> R.drawable.select_omurice
                    else -> R.drawable.select_sam_gye_tang
                }
                var resultImage: ImageView = findViewById(R.id.img_result)
                resultImage.setImageResource(randomResource)
            }
        }

    }
}













//
//        val chinese = "chinese"
//        binding.imgResult.setImageResource(when(chinese){
//            "gob_chang" -> R.drawable.select_gob_chang
//            "jajjang" -> R.drawable.select_jajjang
//            "jjambong" -> R.drawable.select_jjambong
//            "man_do" -> R.drawable.select_man_do
//            "mara_tang" -> R.drawable.select_mara_tang
//            else -> R.drawable.select_yang_kko_chi
//        })
//
//        val western = "western"
//        binding.imgResult.setImageResource(when(western){
//
//            "don_gas" -> R.drawable.select_don_gas
//            "hamburger" -> R.drawable.select_hamburger
//            "hotdog" -> R.drawable.select_hotdog
//            "omurice" -> R.drawable.select_omurice
//            "pasta" -> R.drawable.select_pasta
//            "pizza" -> R.drawable.select_pizza
//            else -> R.drawable.select_sandwich
//        })
//
//        val asian = "asian"
//        binding.imgResult.setImageResource(when(asian){
//            "bibim_bap" -> R.drawable.select_bibim_bap
//            "buchim_gae" -> R.drawable.select_buchim_gae
//            "chicken" -> R.drawable.select_chicken
//            "dak_bal" -> R.drawable.select_dak_bal
//            "do_si_rak" -> R.drawable.select_do_si_rak
//            "gim_bap" -> R.drawable.select_gim_bap
//            "gob_chang" -> R.drawable.select_gob_chang
//            "jajjang" -> R.drawable.select_jajjang
//            "jukkumi" -> R.drawable.select_jukkumi
//            "kalgugsu" -> R.drawable.select_kalgugsu
//            "man_do" -> R.drawable.select_man_do
//            "mara_tang" -> R.drawable.select_mara_tang
//            "ramen" -> R.drawable.select_ramen
//            "sam_gyeob_sal" -> R.drawable.select_sam_gyeob_sal
//            "soyed_crab" -> R.drawable.select_soyed_crab
//            "ssal_gulsu" -> R.drawable.select_ssal_guksu
//            "sundaebokk" -> R.drawable.select_sundaebokk_eum
//            else -> R.drawable.select_yang_kko_chi
//        })
//
//        val japanese = "japanese"
//        binding.imgResult.setImageResource(when(japanese){
//            "don_gas" -> R.drawable.select_don_gas
//            "ramen" -> R.drawable.select_ramen
//            "sashimi" -> R.drawable.select_sashimi
//            else -> R.drawable.select_soyed_crab
//
//        })
//
//        val noodle = "noodle"
//        binding.imgResult.setImageResource(when(noodle){
//            "jajjang" -> R.drawable.select_jajjang
//            "jjambong" -> R.drawable.select_jjambong
//            "kalgugsu" -> R.drawable.select_kalgugsu
//            "mara_tang" -> R.drawable.select_mara_tang
//            "pasta" -> R.drawable.select_pasta
//            "ramen" -> R.drawable.select_ramen
//            else -> R.drawable.select_ssal_guksu
//        })
//        val rice = "rice"
//        binding.imgResult.setImageResource(when(rice){
//            "bibim_bap" -> R.drawable.select_bibim_bap
//            "cheongguck_jang" -> R.drawable.select_cheong_guk_jang
//            "ddek_bokki" -> R.drawable.select_ddek_bokki
//            "doen_jang_jjigae" -> R.drawable.select_doen_jang_jjigae
//            "galbitang" -> R.drawable.select_galbitang
//            "gim_bap" -> R.drawable.select_gim_bap
//            "gob_chang" -> R.drawable.select_gob_chang
//            "gug_bab" -> R.drawable.select_gug_bab
//            "haejang_gug" -> R.drawable.select_haejang_gug
//            "omurice" -> R.drawable.select_omurice
//            else -> R.drawable.select_sam_gye_tang
//        })
//
//        val meat = "meat"
//        binding.imgResult.setImageResource(when(meat){
//            "chicken" -> R.drawable.select_chicken
//            "dak_bal" -> R.drawable.select_dak_bal
//            "jeyugbokk_eum" -> R.drawable.select_jeyugbokk_eum
//            "sam_gyeob_sal" -> R.drawable.select_sam_gyeob_sal
//            "yang_kko_chi" -> R.drawable.select_yang_kko_chi
//            else -> R.drawable.select_yug_hoe
//        })


//        val randomKorean = "korean"
//        val randomResource =
//            binding.imgResult.setImageResource(when(randomKorean){
//            "bibim_bab" -> R.drawable.select_bibim_bap
//            "buchim_gae" -> R.drawable.select_buchim_gae
//            "cheong_guk_jang" -> R.drawable.select_cheong_guk_jang
//            "dak_bal" -> R.drawable.select_dak_bal
//            "ddek_bokki" -> R.drawable.select_ddek_bokki
//            "do_si_rak" -> R.drawable.select_do_si_rak
//            "doen_jang_jjigae" -> R.drawable.select_doen_jang_jjigae
//            "galbitang" -> R.drawable.select_galbitang
//            "gim_bap" -> R.drawable.select_gim_bap
//            "gob_chang" -> R.drawable.select_gob_chang
//            "gug_bab" -> R.drawable.select_gug_bab
//            "haejang_gug" -> R.drawable.select_haejang_gug
//            "jeyugbokk_eum" -> R.drawable.select_jeyugbokk_eum
//            "jukkumi" -> R.drawable.select_jukkumi
//            "kalgugsu" -> R.drawable.select_kalgugsu
//            "sam_gye_tang" -> R.drawable.select_sam_gye_tang
//            "sam_gyeob_sal" -> R.drawable.select_sam_gyeob_sal
//            else -> R.drawable.select_sundaebokk_eum
//        })
//        val resultImage: ImageView = findViewById(R.id.img_result)
//        resultImage.setImageResource(randomResource)


//        val koreanString = intent.getStringArrayListExtra("korean")
//        val chineseString = intent.getStringArrayListExtra("chinese")
//        val westernString = intent.getStringArrayListExtra("western")
//        val asianString = intent.getStringArrayListExtra("asian")
//        val japaneseString = intent.getStringArrayListExtra("japanese")
//        val noodleString = intent.getStringArrayListExtra("noodle")
//        val riceString = intent.getStringArrayListExtra("rice")
//        val meatString = intent.getStringArrayListExtra("meat")
//
//        if (koreanString != null && koreanString.size > 0) {
//            for (s in koreanString) {
//                Log.i("TAG", s)
//            }
//        } else {
//            Log.i("TAG", "aa is null or aa size == 0123")
//        }
//
//
//        if (chineseString != null && chineseString.size > 0) {
//            for (s1 in chineseString) {
//                Log.e("TAG1", s1)
//            }
//        } else {
//            Log.e("TAG1", "aa is null or aa size == 00000000000000000000000000000")
//        }



















//    private fun randomFood() {
//        val randomNumber = (1..korean!!.size)
//        val randomKorean = "korean"
//        val randomResource =
//            binding.imgResult.setImageResource(when(randomKorean){
//            "bibim_bab" -> R.drawable.select_bibim_bap
//            "buchim_gae" -> R.drawable.select_buchim_gae
//            "cheong_guk_jang" -> R.drawable.select_cheong_guk_jang
//            "dak_bal" -> R.drawable.select_dak_bal
//            "ddek_bokki" -> R.drawable.select_ddek_bokki
//            "do_si_rak" -> R.drawable.select_do_si_rak
//            "doen_jang_jjigae" -> R.drawable.select_doen_jang_jjigae
//            "galbitang" -> R.drawable.select_galbitang
//            "gim_bap" -> R.drawable.select_gim_bap
//            "gob_chang" -> R.drawable.select_gob_chang
//            "gug_bab" -> R.drawable.select_gug_bab
//            "haejang_gug" -> R.drawable.select_haejang_gug
//            "jeyugbokk_eum" -> R.drawable.select_jeyugbokk_eum
//            "jukkumi" -> R.drawable.select_jukkumi
//            "kalgugsu" -> R.drawable.select_kalgugsu
//            "sam_gye_tang" -> R.drawable.select_sam_gye_tang
//            "sam_gyeob_sal" -> R.drawable.select_sam_gyeob_sal
//            else -> R.drawable.select_sundaebokk_eum
//        })
//        val resultImage: ImageView = findViewById(R.id.img_result)
//        resultImage.setImageResource(randomResource)


//        koreanString?.let { allRandom.addAll(it) }
//        chineseString?.let {allRandom.addAll(it)}
//        westernString?.let { allRandom.addAll(it) }
//        asianString?.let {allRandom.addAll(it)}
//        japaneseString?.let { allRandom.addAll(it) }
//        chineseString?.let {allRandom.addAll(it)}
//        noodleString?.let { allRandom.addAll(it) }
//        riceString?.let {allRandom.addAll(it)}
//        meatString?.let {allRandom.addAll(it)}

//
//        if (koreanString != null) {
//            allRandom.addAll(koreanString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//
//        if (chineseString != null) {
//            allRandom.addAll(chineseString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (westernString != null) {
//            allRandom.addAll(westernString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (asianString != null) {
//            allRandom.addAll(asianString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (japaneseString != null) {
//            allRandom.addAll(japaneseString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (noodleString != null) {
//            allRandom.addAll(noodleString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (riceString != null) {
//            allRandom.addAll(riceString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (meatString != null) {
//            allRandom.addAll(meatString)
//            println("randomFood:--------------------------------")
//            Log.d(TAG, "randomFood:--------------------------------")
//        }
//        if (meatString == null) {
//            println("randomFood:+++++++++++++++++++++++++++++++++++++++++")
//            Log.d(TAG, "randomFood:++++++++++++++++++++++++++++++++++++++++++++")
//        }


//
//
//
//
//


    //
//        val randomNumber = (0 until koreanString!!.size).random()
//        val randomResource = when (randomNumber) {
//            1 -> R.drawable.select_bibim_bap
//            2 -> R.drawable.select_buchim_gae
//            3 -> R.drawable.select_cheong_guk_jang
//            4 -> R.drawable.select_chicken
//            5 -> R.drawable.select_dak_bal
//            6 -> R.drawable.select_ddek_bokki
//            7 -> R.drawable.select_do_si_rak
//            8 -> R.drawable.select_doen_jang_jjigae
//            9 -> R.drawable.select_don_gas
//            10 -> R.drawable.select_galbitang
//
//            11 -> R.drawable.select_gim_bap
//            12 -> R.drawable.select_gob_chang
//            13 -> R.drawable.select_gug_bab
//            14 -> R.drawable.select_haejang_gug
//            15 -> R.drawable.select_hamburger
//            16 -> R.drawable.select_hotdog
//            17 -> R.drawable.select_jajjang
//            18 -> R.drawable.select_jeyugbokk_eum
//            19 -> R.drawable.select_jjambong
//            20 -> R.drawable.select_jukkumi
//
//            21 -> R.drawable.select_kalgugsu
//            22 -> R.drawable.select_man_do
//            23 -> R.drawable.select_mara_tang
//            24 -> R.drawable.select_omurice
//            25 -> R.drawable.select_pasta
//            26 -> R.drawable.select_pizza
//            27 -> R.drawable.select_ramen
//            28 -> R.drawable.select_sam_gye_tang
//            29 -> R.drawable.select_sam_gyeob_sal
//            30 -> R.drawable.select_sandwich
//
//            31 -> R.drawable.select_sashimi
//            32 -> R.drawable.select_soyed_crab
//            33 -> R.drawable.select_ssal_guksu
//            34 -> R.drawable.select_sundaebokk_eum
//            35 -> R.drawable.select_yang_kko_chi
//            else -> R.drawable.select_yug_hoe
//        }
//
//        val resultImage: ImageView = findViewById(R.id.img_result)
//        resultImage.setImageResource(randomResource)


//         val resultImage: ImageView = findViewById(R.id.img_result)
//        resultImage.setImageResource(randomResource)




//    }
//
//     fun  randomFood() {
//         var allRandom = ArrayList<String>();   //큰 ArrayList,아래 값들 받아서 사용한다,
//
//         if (intent.hasExtra("korean")) {   //SelectActivity에서 "korean"누르면 korean 배열에 있는 값을 allRamdom에 추가한다.
//             allRandom.addAll(listOf("korean"))
//         }
//         if (intent.hasExtra("chinese")) {
//             allRandom.addAll(listOf("chinese"))
//         }
//         if (intent.hasExtra("western")) {
//             allRandom.addAll(listOf("western"))
//         }
//         if (intent.hasExtra("asian")) {
//             allRandom.addAll(listOf("asian"))
//         }
//         if (intent.hasExtra("japanese")) {
//             allRandom.addAll(listOf("japanese"))
//         }
//         if (intent.hasExtra("noodle")) {
//                 allRandom.addAll(listOf("noodle"))
//         }
//         if (intent.hasExtra("rice")) {
//                 allRandom.addAll(listOf("rice"))
//         }
//         if (intent.hasExtra("meat")) {
//                 allRandom.addAll(listOf("meat"))
//
//         }
//         val randomNumber = (1..allRandom.size).random() //allRandom의 길이의 수 중 1개의 값 랜덤 뽑기
     //         val resultImage: ImageView = findViewById(R.id.img_result)
//         resultImage.setImageResource(randomNumber)


//            for(i in 0 until allRandom.size){
//                var bmp : Int = getResources().getIdentifier("select_" + allRandom[i] + ".png","drawable",packageName)
//                val bitmap: Bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),bmp),
//                    70,70,false)
//                FoodList.add(bitmap)
//            }
//         var set: TreeSet<Int> = TreeSet()
//         val random = Random()
//         val num = random.nextInt(45)
//         set.add(num)
//


//         var randomNumber = (0 until allRandom.size).random() //allRandom의 길이의 수 중 1개의 값 랜덤 뽑기
//         var foodID : Int =  getResources().getIdentifier("select_" + allRandom[randomNumber] + ".png",
//             "id", packageName )
//         val imgView = findViewById<ImageView>(foodID)
//         imgView.setImageResource(foodID)

//         val iv = findViewById<View>(R.id.imgView) as ImageView
//         iv.setImageResource(resID) // 이미지뷰의 이미지를 설정한다;

//
//        val selectedImage = allRandom[randomNumber]
//         val resultImage: ImageView = findViewById(R.id.img_result)
//         resultImage.setImageResource(selectedImage)



