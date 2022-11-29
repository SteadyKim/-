package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.databinding.FragmentSelectBinding
import com.example.kotlinproject.utils.RandomFood
import com.example.kotlinproject.utils.RandomFood.Companion.ANYTHINGFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.ASIANFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.CHINESEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.JAPANESEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.KOREANFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.MEATFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.NOODLEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.RICEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.WESTERNFOOD

class SelectFragment : Fragment() {
    var binding : FragmentSelectBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFoodBtnListener()

    }

    private fun initFoodBtnListener() {
        binding?.btnAnything?.setOnClickListener {
            val anythingFood = RandomFood.getAnyThing()
            val bundle = Bundle().apply {
                putStringArrayList(ANYTHINGFOOD, anythingFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnKorean?.setOnClickListener {
            val koreanFood = RandomFood.getKoreanFood()
            val bundle = Bundle().apply {
                putStringArrayList(KOREANFOOD, koreanFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnChinese?.setOnClickListener {
            val chineseFood = RandomFood.getChineseFood()
            val bundle = Bundle().apply {
                putStringArrayList(CHINESEFOOD, chineseFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnWestern?.setOnClickListener {
            val westernFood = RandomFood.getWesternFood()
            val bundle = Bundle().apply {
                putStringArrayList(WESTERNFOOD, westernFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnAsian?.setOnClickListener {
            val asianFood = RandomFood.getAsianFood()
            val bundle = Bundle().apply {
                putStringArrayList(ASIANFOOD, asianFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnJapanese?.setOnClickListener {
            val japanenseFood = RandomFood.getJapaneseFood()
            val bundle = Bundle().apply {
                putStringArrayList(JAPANESEFOOD, japanenseFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnNoodle?.setOnClickListener {
            val noodleFood = RandomFood.getNoodleFood()
            val bundle = Bundle().apply {
                putStringArrayList(NOODLEFOOD, noodleFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnMeat?.setOnClickListener {
            val meatFood = RandomFood.getMeatFood()
            val bundle = Bundle().apply {
                putStringArrayList(MEATFOOD, meatFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }

        binding?.btnRice?.setOnClickListener {
            val riceFood = RandomFood.getRiceFood()
            val bundle = Bundle().apply {
                putStringArrayList(RICEFOOD, riceFood)
            }
            findNavController().navigate(R.id.action_selectFragment_to_resultFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}