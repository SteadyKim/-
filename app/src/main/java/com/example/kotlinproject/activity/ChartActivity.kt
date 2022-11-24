package com.example.kotlinproject.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinproject.databinding.ActivityChartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class ChartActivity : AppCompatActivity() {

    lateinit var pieChart : PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChartBinding.inflate(layoutInflater);

        setContentView(binding.root)


        pieChart = binding.chartPie

        val list: ArrayList<PieEntry> = ArrayList()

        list.add(PieEntry(10f, "피자"))
        list.add(PieEntry(20f,"햄버거"))
        list.add(PieEntry(20f,"치킨"))
        list.add(PieEntry(40f,"고기"))
        list.add(PieEntry(10f,"밥"))


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
        pieChart.data = pieData
        pieChart.holeRadius = 0f
        pieChart.transparentCircleRadius = 0f
        pieChart.description.text = "음식 선호도"
        pieChart.legend.textColor = Color.GRAY
        pieChart.centerText = ""

        pieChart.animateY(1500)



    }
}
