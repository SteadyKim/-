package com.example.kotlinproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinproject.databinding.ActivityHistoryBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.enum.FoodNames
import com.example.kotlinproject.recyclerview.RecyclerViewAdapter
import com.google.firebase.database.*
import java.time.LocalDate
import java.util.*


class HistoryActivity : AppCompatActivity() {

    val TAG = "HistoryActivity"
    var db: AppDatabase? = null
    var foodsList = mutableListOf<Foods>()

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()
    lateinit var mySnapShot : DataSnapshot
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
        /**
         * Firebase초기화
         */
        initFirebase()
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
                /**
                 * Firebase 데이터 지우기
                 */
                deleteFirebaseData(foods)
            }

            private fun deleteFirebaseData(foods: Foods) {
                if (::mySnapShot.isInitialized) {
                    val foodsSnapShot = mySnapShot.child("Foods")
                    foodsSnapShot.value

                    //for 문 돌면서 그 key의 id가 같으면 삭제하기
                    for (ds in foodsSnapShot.children) {
                        val key = ds.key.toString()
                        val selectedId = ds.getValue(Foods::class.java)?.id
                        if (foods.id == selectedId) {
                            databaseReference.child("Foods").child(key).removeValue()
                            break
                        }
                    }
                }
            }
        })

        binding.mRecyclerView.adapter = adapter


        //플러스 버튼 클릭 : 데이터 추가
        initPlusBtn_나중에지울예정(binding, adapter)

        //버튼 클릭시 ChartActivity로 이통
        initChartBtn(binding)
    }

    private fun initPlusBtn_나중에지울예정(binding: ActivityHistoryBinding, adapter: RecyclerViewAdapter) {
        binding.btnPlus.setOnClickListener {

            //랜덤 번호 만들기
            val range = (1..4)
            val random = range.random()

            // 현재 날짜 구하기
            val today: LocalDate = LocalDate.now()
            val foodName = when (random) {
                1 -> FoodNames.CHEONG_GOK_JANG
                2 -> FoodNames.CHICKEN
                3 -> FoodNames.BIBIM_BAP
                else -> FoodNames.BUCHIM_GAE
            }

            //uuid 생성
            var random_uuid = UUID.randomUUID()
            val food = Foods(random_uuid.toString(), foodName, today.toString())
            foodsList.add(food) //리스트 추가
            db?.FoodsDao()?.insertAll(food)
            databaseReference.child("Foods").push().setValue(food)
            adapter.notifyDataSetChanged() //리스트뷰 갱신
        }
    }

    private fun initChartBtn(binding: ActivityHistoryBinding) {
        binding.btnChart.setOnClickListener {
            val nextIntent = Intent(this@HistoryActivity, ChartActivity::class.java)
            startActivity(nextIntent)
        }
    }

    private fun initFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //late init
                mySnapShot = snapshot
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}