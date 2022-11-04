package com.example.kotlinproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.databinding.ActivityHistoryBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.enum.FoodNames
import com.example.kotlinproject.recyclerview.RecyclerViewAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate


class HistoryActivity : AppCompatActivity() {

    val TAG = "HistoryActivity"
    var db: AppDatabase? = null
    var foodsList = mutableListOf<Foods>()

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DB 초기화
        db = AppDatabase.getInstance(this)

        //이전에 저장한 내용 모두 불러와서 추가하기
        val savedFoods = db!!.FoodsDao().getAll()
        if (savedFoods.isNotEmpty()) {
            foodsList.addAll(savedFoods)
        }

        //어댑터, 아이템 클릭 : 아이템 삭제
        val adapter = RecyclerViewAdapter(foodsList)

        adapter.setItemClickListener(object : RecyclerViewAdapter.ItemClickListener {

            override fun onDeleteClick(position: Int) {
                /**
                 * 콜백 받음
                 */
                val foods = foodsList[position]
                db?.FoodsDao()?.delete(foods = foods) //DB에서 삭제
                foodsList.removeAt(position) //리스트에서 삭제
                adapter.notifyDataSetChanged() //리스트뷰 갱신

                Log.d(TAG, "remove item($position). name:${foods.name}")
            }
        })

        binding.mRecyclerView.adapter = adapter


        //플러스 버튼 클릭 : 데이터 추가
        binding.btnPlus.setOnClickListener {

            //랜덤 번호 만들기
            val range = (1..4)
            val random = range.random()

            // 현재 날짜 구하기
            val today: LocalDate = LocalDate.now()
            val foodName = when(random) {
                1 -> FoodNames.CHEONG_GOK_JANG
                2 -> FoodNames.CHICKEN
                3 -> FoodNames.BIBIM_BAP
                else -> FoodNames.BUCHIM_GAE
            }


            val food = Foods(0, foodName, today.toString())
            foodsList.add(food) //리스트 추가
            db?.FoodsDao()?.insertAll(food)
            adapter.notifyDataSetChanged() //리스트뷰 갱신
        }

        //버튼 클릭시 ChartActivity로 이통
        binding.btnChart.setOnClickListener {
            val nextIntent = Intent(this@HistoryActivity, ChartActivity::class.java)
            startActivity(nextIntent)
        }
    }
}