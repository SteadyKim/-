package com.example.kotlinproject

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kotlinproject.databinding.FragmentChartBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.viewmodel.MyViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

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
            foodsList = viewModel.foodList.value ?: ArrayList<Foods>()

            if(foodsList.isEmpty()) {
                binding?.chartPie?.visibility = View.INVISIBLE
                binding?.txtInfo?.visibility = View.VISIBLE
            }
            else {
                pieChart = binding?.chartPie

                val foodMap = mutableMapOf<String, Int>()
                for (food in foodsList) {
                    if(foodMap.containsKey(food.name)) {
                        val tmp = foodMap[food.name]

                        if (tmp != null) {
                            foodMap[food.name] = tmp + 1
                        }
                    }else {
                        foodMap[food.name] = 1
                    }
                }
                val pieChartDataList: ArrayList<PieEntry> = makePieChartDataList(foodMap)

                initPieChart(pieChartDataList)

                binding?.chartPie?.visibility = View.VISIBLE
                binding?.txtInfo?.visibility = View.INVISIBLE
            }
        }
    }



////        이전에 저장한 내용 모두 불러와서 추가하기 - Room 사용
//        val savedFoods = db!!.FoodsDao().getAll()
//        if (savedFoods.isNotEmpty()) {
////             생명주기상 onViewCreated 시 recyclerView에 계속 추가되는 오류 해결하기
//            if (foodsList.isEmpty()) {
//                foodsList.addAll(savedFoods)
//            }
//        }



    private fun makePieChartDataList(foodMap: MutableMap<String, Int>): ArrayList<PieEntry> {
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

        pieDataSet.valueTextSize = 9f
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