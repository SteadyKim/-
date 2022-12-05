package com.example.kotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlinproject.databinding.ActivityFirstBinding
import com.example.kotlinproject.databinding.ActivityMain2Binding
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_main2.*

class FirstActivity : AppCompatActivity() {

    private var vpAdapter: FragmentStateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_first)
        val binding = ActivityFirstBinding.inflate(layoutInflater)

        setContentView(binding.root)

        vpAdapter = CustomPagerAdapter(this)
        viewPager2.adapter = vpAdapter

        binding.btnSkip.setOnClickListener{
            val intent = Intent(this,Main2Activity::class.java)
            startActivity(intent)
            finish()
        }

    }
    class CustomPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        private val PAGENUMBER = 4

        override fun getItemCount(): Int {
            return PAGENUMBER
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TestFragment.newInstance(R.raw.img00, "오늘 뭐먹지?")
                1 -> TestFragment.newInstance(R.raw.img01, "음식 메뉴 추천받기")
                2 -> TestFragment.newInstance(R.raw.img02, "차트로 한눈에 보기")
                3 -> TestFragment.newInstance(R.raw.img03, "카카오톡 공유 가능")
                else -> TestFragment.newInstance(R.raw.img00, "오늘 뭐먹지")
            }
        }
    }


}