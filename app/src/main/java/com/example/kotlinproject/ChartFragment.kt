package com.example.kotlinproject

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinproject.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class ChartFragment : Fragment() {
    var pieChart: PieChart? = null
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
            binding?.chartPie?.visibility = View.INVISIBLE
            binding?.txtInfo?.visibility = View.VISIBLE
        }
        else {
            pieChart = binding?.chartPie

            val foodMap = mutableMapOf<String, Int>()

            for (food in foodsList) {
                if(foodMap.containsKey(food)) {
                    val tmp = foodMap[food]

                    if (tmp != null) {
                        foodMap[food] = tmp + 1
                    }
                }else {
                    foodMap[food] = 1
                }
            }
            val list: ArrayList<PieEntry> = makePieDateList(foodMap)


            initPieChart(list)

            binding?.chartPie?.visibility = View.VISIBLE
            binding?.txtInfo?.visibility = View.INVISIBLE
        }
    }

    private fun makePieDateList(foodMap: MutableMap<String, Int>): ArrayList<PieEntry> {
        // 정렬하기
        val sortedFoodMap = foodMap.toList().sortedByDescending { it.second }.toMap().toMutableMap()
        var sumi = 0

        // 길이가 5 이상이라면 상위 5개만 체크한다.
        var flag = 0
        var pieTmpDataMap = mutableMapOf<String, Float>()
        if (sortedFoodMap.size >= 5) {
            for ((key, value) in sortedFoodMap) {

                //subMap 만들기
                pieTmpDataMap[key] = value.toFloat()

                // sum 계산하기
                sumi += value
                flag++

                if (flag == 5) {
                    break
                }
            }
        }

        // 비율 계산하기
        for ((key, value) in pieTmpDataMap) {
            pieTmpDataMap[key] = (value / sumi) * 100
        }
        //PieChart 데이터 만들기
        val list: ArrayList<PieEntry> = ArrayList()

        for ((key, value) in pieTmpDataMap) {
            val newValue = value.toInt().toFloat()
            list.add(PieEntry(newValue, key))
        }
        return list
    }

    private fun initPieChart(list: ArrayList<PieEntry>) {
        val pieDataSet = PieDataSet(list, "")

        pieDataSet.setColors(
            Color.parseColor("#63bcff"),
            Color.parseColor("#5cf769"),
            Color.parseColor("#f7785c"),
            Color.parseColor("#db5bf5"),
            Color.parseColor("#f75cd3"),
            200
        )

        pieDataSet.valueTextSize = 15f
        pieDataSet.valueTextColor = Color.WHITE

        val pieData = PieData(pieDataSet)
        pieChart?.data = pieData
        pieChart?.holeRadius = 0f
        pieChart?.transparentCircleRadius = 0f
        pieChart?.description?.text = "음식 선호도"
        pieChart?.legend?.textColor = Color.GRAY
        pieChart?.centerText = ""

        pieChart?.animateY(1500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}