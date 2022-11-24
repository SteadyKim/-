package com.example.kotlinproject.activity

//TODO bundle getPutEn()
//TODO arrayList
// do

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ActivityResultBinding
import com.example.kotlinproject.db.entity.Foods
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.util.*


class ResultActivity : AppCompatActivity() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()
    lateinit var binding: ActivityResultBinding
    lateinit var selectedFood : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater);

        setContentView(binding.root)

        doResult()

        binding.btnRedo.setOnClickListener {
            doResult()
        }

        binding.btnMap.setOnClickListener {
            var random_uuid = UUID.randomUUID()
            val today: LocalDate = LocalDate.now()
            val food = Foods(random_uuid.toString(), selectedFood, today.toString())
            databaseReference.child("Foods").push().setValue(food)

            //TODO bundle에 담아서 selectedFood 이름 String으로 MapsActivity에 보낸다.
            val intent = Intent(this@ResultActivity, MapsActivity::class.java )
            intent.putExtra("food", selectedFood)
            startActivity(intent)
        }


    }


    private fun doResult() {
        val anything = intent.getStringArrayListExtra("anything")
        val korean = intent.getStringArrayListExtra("korean")
        val chinese = intent.getStringArrayListExtra("chinese")
        val western = intent.getStringArrayListExtra("western")
        val asian = intent.getStringArrayListExtra("asian")
        val japanese = intent.getStringArrayListExtra("japanese")
        val noodle = intent.getStringArrayListExtra("noodle")
        val meat = intent.getStringArrayListExtra("meat")
        val rice = intent.getStringArrayListExtra("rice")


        setDrawable(anything, korean, chinese, western, asian, japanese, noodle, meat, rice)
    }

    private fun setDrawable(
        anything: ArrayList<String>?,
        korean: ArrayList<String>?,
        chinese: ArrayList<String>?,
        western: ArrayList<String>?,
        asian: ArrayList<String>?,
        japanese: ArrayList<String>?,
        noodle: ArrayList<String>?,
        meat: ArrayList<String>?,
        rice: ArrayList<String>?
    ) {
        if (intent.hasExtra("anything")) {

            if (anything != null) {
                var randomNumber = (1..anything.size).random()

                selectedFood = anything[randomNumber-1]
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

                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }

        if (intent.hasExtra("korean")) {
            if (korean != null) {
                var randomNumber = (1..korean.size).random()
                selectedFood = korean[randomNumber-1]

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
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("chinese")) {
            if (chinese != null) {
                var randomNumber = (1..chinese.size).random()
                selectedFood = chinese[randomNumber-1]

                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_gob_chang
                    2 -> R.drawable.select_jajjang
                    3 -> R.drawable.select_jjambong
                    4 -> R.drawable.select_man_do
                    5 -> R.drawable.select_mara_tang
                    else -> R.drawable.select_yang_kko_chi
                }
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }

        if (intent.hasExtra("western")) {
            if (western != null) {
                var randomNumber = (1..western.size).random()
                selectedFood = western[randomNumber-1]

                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_don_gas
                    2 -> R.drawable.select_hamburger
                    3 -> R.drawable.select_hotdog
                    4 -> R.drawable.select_omurice
                    5 -> R.drawable.select_pasta
                    6 -> R.drawable.select_pizza
                    else -> R.drawable.select_sandwich

                }
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
        //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+--+-+-+-+-+-+-+-+
        if (intent.hasExtra("asian")) {
            if (asian != null) {
                var randomNumber = (1..asian.size).random()
                selectedFood = asian[randomNumber-1]

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
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("japanese")) {
            if (japanese != null) {
                var randomNumber = (1..japanese.size).random()
                selectedFood = japanese[randomNumber-1]


                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_don_gas
                    2 -> R.drawable.select_ramen
                    3 -> R.drawable.select_sashimi
                    else -> R.drawable.select_soyed_crab
                }
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("noodle")) {
            if (noodle != null) {
                var randomNumber = (1..noodle.size).random()
                selectedFood = noodle[randomNumber-1]


                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_jajjang
                    2 -> R.drawable.select_jjambong
                    3 -> R.drawable.select_kalgugsu
                    4 -> R.drawable.select_mara_tang
                    5 -> R.drawable.select_pasta
                    6 -> R.drawable.select_ramen
                    else -> R.drawable.select_ssal_guksu
                }
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("meat")) {
            if (meat != null) {
                var randomNumber = (1..meat.size).random()
                selectedFood = meat[randomNumber-1]

                var randomResource = when (randomNumber) {
                    1 -> R.drawable.select_chicken
                    2 -> R.drawable.select_dak_bal
                    3 -> R.drawable.select_jeyugbokk_eum
                    4 -> R.drawable.select_sam_gyeob_sal
                    5 -> R.drawable.select_yang_kko_chi
                    else -> R.drawable.select_yug_hoe
                }
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
        if (intent.hasExtra("rice")) {
            if (rice != null) {
                var randomNumber = (1..rice.size).random()
                selectedFood = rice[randomNumber-1]

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
                var resultImage: ImageView = binding.imgResult
                resultImage.setImageResource(randomResource)
            }
        }
    }
}


