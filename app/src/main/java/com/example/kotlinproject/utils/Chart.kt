package com.example.kotlinproject.utils

import android.graphics.Color
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class Chart {
    companion object {

        fun initPieChart(param: PieChart?): PieChart? {
            var pieChart = param

            return pieChart
        }

        fun getPieDataList(foodMap: MutableMap<String, Int>): ArrayList<PieEntry> {
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

        fun setPieChartDesign(list: ArrayList<PieEntry>, pieChart: PieChart?) {
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
    }
}