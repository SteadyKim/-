package com.example.kotlinproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.databinding.ActivitySelectBinding
import com.example.kotlinproject.enum.FoodNames
import com.example.kotlinproject.enum.FoodNames.Companion.BIBIM_BAP
import com.example.kotlinproject.enum.FoodNames.Companion.BUCHIM_GAE
import com.example.kotlinproject.enum.FoodNames.Companion.CHEONGGUCK_JANG
import com.example.kotlinproject.enum.FoodNames.Companion.CHICKEN
import com.example.kotlinproject.enum.FoodNames.Companion.DAK_BAL
import com.example.kotlinproject.enum.FoodNames.Companion.DDEK_BOKKI
import com.example.kotlinproject.enum.FoodNames.Companion.DOEN_JANG_JJIGAE
import com.example.kotlinproject.enum.FoodNames.Companion.DON_GAS
import com.example.kotlinproject.enum.FoodNames.Companion.DO_SI_RAK
import com.example.kotlinproject.enum.FoodNames.Companion.GALBITANG
import com.example.kotlinproject.enum.FoodNames.Companion.GIM_BAP
import com.example.kotlinproject.enum.FoodNames.Companion.GOB_CHANG
import com.example.kotlinproject.enum.FoodNames.Companion.GUG_BAB

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

        binding.btnMap2.setOnClickListener {
            val intent = Intent(this@SelectActivity, MapsActivity::class.java)
            startActivity(intent)
        }

        val anythingFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val anything: ArrayList<String> = ArrayList()
            anything.add(BIBIM_BAP)
            anything.add(BUCHIM_GAE)
            anything.add(CHEONGGUCK_JANG)
            anything.add(CHICKEN)
            anything.add(DAK_BAL)
            anything.add(DDEK_BOKKI)
            anything.add(DO_SI_RAK)
            anything.add(DOEN_JANG_JJIGAE)
            anything.add(DON_GAS)
            anything.add(GALBITANG)

            anything.add(GIM_BAP)
            anything.add(GOB_CHANG)
            anything.add(GUG_BAB)
            anything.add("haejang_gug")
            anything.add("hamburger")
            anything.add("hotdog")
            anything.add("jajjang")
            anything.add("jjambong")
            anything.add("jukkumi")

            anything.add("kalgugsu")
            anything.add("man_do")
            anything.add("mara_tang")
            anything.add("omurice")
            anything.add("pasta")
            anything.add("pizza")
            anything.add("ramen")
            anything.add("sam_gye_tang")
            anything.add("sam_gyeob_sal")
            anything.add("sandwich")

            anything.add("sashimi")
            anything.add("soyed_crab")
            anything.add("ssal_gulsu")

            anything.add("sundaebokk")
            anything.add("yang_kko_chi")
            anything.add("yug_hoe")
            anything.add("jeyugbokk_eum")
            intent.putStringArrayListExtra("anything", anything)
            startActivity(intent)
        }

        val koreanFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val korean: ArrayList<String> = ArrayList()

            korean.add("bibim_bap")
            korean.add("buchim_gae")
            korean.add("cheong_guk_jang")
            korean.add("dak_bal")
            korean.add("ddek_bokki")
            korean.add("do_si_rak")
            korean.add("doen_jang_jjigae")
            korean.add("galbitang")
            korean.add("gim_bap")
            korean.add("gob_chang")

            korean.add("gug_bab")
            korean.add("haejang_gug")
            korean.add("jeyugbokk_eum")
            korean.add("jukkumi")
            korean.add("kalgugsu")
            korean.add("gug_bab")
            korean.add("sam_gye_tang")
            korean.add("galbitang")
            korean.add("sam_gyeob_sal")
            korean.add("sundaebokke")
            intent.putStringArrayListExtra("korean", korean)
            startActivity(intent)
        }
        val chineseFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val chinese: ArrayList<String> = ArrayList()
            chinese.add("gob_chang")
            chinese.add("jajjang")
            chinese.add("jjambong")
            chinese.add("man_do")
            chinese.add("mara_tang")
            chinese.add("yang_kko_chi")
            intent.putStringArrayListExtra("chinese", chinese)
            startActivity(intent)
        }

        val westernFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val western: ArrayList<String> = ArrayList()
            western.add("don_gas")
            western.add("hamburger")
            western.add("hotdog")
            western.add("omurice")
            western.add("pasta")
            western.add("pizza")
            western.add("sandwich")
            intent.putStringArrayListExtra("western", western)
            startActivity(intent)
        }

        val asianFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val asian: ArrayList<String> = ArrayList()
            asian.add("bibim_bap")
            asian.add("buchim_gae")
            asian.add("chicken")
            asian.add("dak_bal")
            asian.add("do_si_rak")
            asian.add("gim_bap")
            asian.add("gob_chang")
            asian.add("jajjang")
            asian.add("jukkumi")
            asian.add("kalgugsu")

            asian.add("man_do")
            asian.add("mara_tang")
            asian.add("ramen")
            asian.add("sam_gyeob_sal")
            asian.add("soyed_crab")
            asian.add("ssal_gulsu")
            asian.add("jjambong")
            asian.add("yang_kko_chi")
            intent.putStringArrayListExtra("asian", asian)
            startActivity(intent)
        }
        val japaneseFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val japanese: ArrayList<String> = ArrayList()
            japanese.add("don_gas")
            japanese.add("ramen")
            japanese.add("sashimi")
            japanese.add("soyed_crab")
            intent.putStringArrayListExtra("japanese", japanese)
            startActivity(intent)
        }
        val noodleFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val noodle: ArrayList<String> = ArrayList()
            noodle.add("jajjang")
            noodle.add("jjambong")
            noodle.add("kalgugsu")
            noodle.add("mara_tang")
            noodle.add("pasta")
            noodle.add("ramen")
            noodle.add("ssal_gulsu")
            intent.putStringArrayListExtra("noodle", noodle)
            startActivity(intent)
        }
        val meatFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val meat: ArrayList<String> = ArrayList()
            meat.add("chicken")
            meat.add("dak_bal")
            meat.add("jeyugbokk_eum")
            meat.add("sam_gyeob_sal")
            meat.add("yang_kko_chi")
            meat.add("yug_hoe")
            intent.putStringArrayListExtra("meat", meat)
            startActivity(intent)
        }
        val riceFunction: (View) -> Unit = {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java)
            val rice: ArrayList<String> = ArrayList()
            rice.add("bibim_bap")
            rice.add("cheongguck_jang")
            rice.add("doen_jang_jjigae")
            rice.add("do_si_rak")
            rice.add("galbitang")
            rice.add("gim_bap")
            rice.add("haejang_gug")
            rice.add("gug_bab")
            rice.add("omurice")
            rice.add("sam_gye_tang")
            intent.putStringArrayListExtra("rice", rice)
            startActivity(intent)

        }
        binding.btnAnything.setOnClickListener(anythingFunction)
        binding.btnKorean.setOnClickListener(koreanFunction)
        binding.btnChinese.setOnClickListener(chineseFunction)
        binding.btnWestern.setOnClickListener(westernFunction)
        binding.btnAsian.setOnClickListener(asianFunction)
        binding.btnJapanese.setOnClickListener(japaneseFunction)
        binding.btnNoodle.setOnClickListener(noodleFunction)
        binding.btnMeat.setOnClickListener(meatFunction)
        binding.btnRice.setOnClickListener(riceFunction)

    }
}

