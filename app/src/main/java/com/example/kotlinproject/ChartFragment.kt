package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinproject.databinding.FragmentChartBinding


class ChartFragment : Fragment() {

    var binding : FragmentChartBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodsList = arguments?.getStringArrayList("foodsList")!!

        if(foodsList.isEmpty()) {
            println("ChartFragment: 아직 값이 없습니다.")
            binding?.chartPie?.visibility = View.INVISIBLE
            binding?.txtInfo?.visibility = View.VISIBLE
        }
        else {
            binding?.chartPie?.visibility = View.VISIBLE
            binding?.txtInfo?.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}