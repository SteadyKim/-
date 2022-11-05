package com.example.kotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.kotlinproject.databinding.ActivityChartBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

class ChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val binding = ActivityChartBinding.inflate(layoutInflater);
        var barChart: BarChart = findViewById(R.id.chart_bar)
        val entries = ArrayList<BarEntry>()
        entries.add((BarEntry(1.0f, 2.0f)))
        entries.add((BarEntry(2.0f, 7.0f)))
        entries.add((BarEntry(3.0f, 3.0f)))
        entries.add((BarEntry(4.0f, 9.0f)))
        entries.add((BarEntry(5.0f, 7.0f)))
        entries.add((BarEntry(6.0f, 3.0f)))
        entries.add((BarEntry(1.0f, 9.0f)))

        barChart.run {
            description.isEnabled = false //차트 옆에 별도 표기되는 description 보이게
            setMaxVisibleValueCount(7) //최대 보이는 그래프 개수 7개
            setPinchZoom(false) //두손가락으로 줌인 줌아웃 설정
            setDrawBarShadow(false) //그래프 그림자
            setDrawGridBackground(false) //격자구조 넣는지

            axisLeft.run { //y 방향축
                axisMaximum = 10f
                axisMinimum = 0f // 최소값 0
                granularity = 2f // 50 단위마다 선을 그리려고 설정.
                setDrawLabels(true) // 값 적는거 허용
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                axisLineColor = ContextCompat.getColor(
                    context,
                    com.google.android.material.R.color.design_default_color_secondary_variant
                ) //축 색깔 설정
                gridColor = ContextCompat.getColor(
                    context,
                    com.google.android.material.R.color.design_default_color_secondary_variant
                ) // 축 아닌 격자 색깔 설정
                textColor = ContextCompat.getColor(
                    context,
                    R.color.black
                ) // 라벨 텍스트 컬러 설정
                textSize = 10f //라벨 텍스트 크기
            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에다가 둔다.
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(true) // 축 그림
                setDrawGridLines(false) // 격자
                textColor = ContextCompat.getColor(
                    context,
                    com.google.android.material.R.color.design_default_color_secondary_variant
                ) //라벨 색상
                textSize = 10f // 텍스트 크기
                valueFormatter = MyXAxisFormatter()
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }
        var set = BarDataSet(entries, "DataSet") //데이터셋 초기화
        set.color = ContextCompat.getColor(
            applicationContext!!,
            com.google.android.material.R.color.design_default_color_secondary_variant
        ) //바 그래프 색 설정
        val dataSet: ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.3f //막대 너비 설정
        barChart.run {
            this.data = data //차트의 데이터를 data로 설정해줌.
            setFitBars(true)
            invalidate()
        }

        binding.btnChart2.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("타이틀입니다")
                .setMessage("메세지 내용 부분입니다.")

            builder.show()
        }

        binding.map.setOnClickListener {
            val nextIntent = Intent(this@ChartActivity, SelectActivity::class.java)
            startActivity(nextIntent)
        }
    }

    inner class MyXAxisFormatter : ValueFormatter() {
        //private val days = arrayOf("1차", "2차", "3차", "4차", "5차", "6차", "7차")
        private val days = arrayOf("파스타", "곱창", "김밥", "짜장면", "치킨", "떡볶이")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt() - 1) ?: value.toString()
        }
    }
}
