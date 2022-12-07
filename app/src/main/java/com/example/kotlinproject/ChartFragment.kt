package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kotlinproject.databinding.FragmentChartBinding
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.utils.Chart
import com.example.kotlinproject.viewmodel.MyViewModel
import com.github.mikephil.charting.charts.PieChart

class ChartFragment : Fragment() {
    val viewModel: MyViewModel by activityViewModels()
    var foodsList = ArrayList<Foods>()
    var pieChart: PieChart? = null
    var binding : FragmentChartBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * View와 ViewModel이 만나는 지점
         */
        viewModel.foodList.observe(viewLifecycleOwner) {
            foodsList = viewModel.foodList.value ?: ArrayList()

            if(foodsList.size in 0..4) {
                binding?.chartPie?.visibility = View.INVISIBLE
                binding?.txtInfo?.visibility = View.VISIBLE
                binding?.txtInfo?.text = "데이터를 집계중입니다!!"
            }
            else {
                binding?.chartPie?.visibility = View.VISIBLE
                binding?.txtInfo?.visibility = View.INVISIBLE

                //수업시간에 배운 Companion Object를 활용해 OOP를 적용 시도
                pieChart = Chart.initPieChart(binding?.chartPie)

                val foodMap = getFoodMap(foodsList)

                val pieDataList = Chart.getPieDataList(foodMap)

                Chart.setPieChartDesign(pieDataList, pieChart)

            }
        }
    }

    //Array 상태인 데이터를 Map으로 변환
    private fun getFoodMap(foodsList: ArrayList<Foods>): MutableMap<String, Int> {
        val foodMap = mutableMapOf<String, Int>()
        for (food in foodsList) {
            if (foodMap.containsKey(food.name)) {
                val tmp = foodMap[food.name]

                if (tmp != null) {
                    foodMap[food.name] = tmp + 1
                }
            } else {
                foodMap[food.name] = 1
            }
        }
        return foodMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}