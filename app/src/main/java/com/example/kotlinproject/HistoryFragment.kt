package com.example.kotlinproject

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.databinding.ActivityHistoryBinding
import com.example.kotlinproject.databinding.FragmentHistoryBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.RandomFood
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.recyclerview.RecyclerViewAdapter
import com.google.firebase.database.*
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.*


class HistoryFragment : Fragment() {
    //Room 관련 변수
    var db: AppDatabase? = null
    var foodsList = ArrayList<Foods>()
    var adapter: RecyclerViewAdapter? = null
    //fireBase 변수
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()
    lateinit var mySnapShot : DataSnapshot

    var binding: FragmentHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //DB 초기화
        db = AppDatabase.getInstance(requireContext())
        /**
         * Firebase초기화
         */
        initFirebase()

        //이전에 저장한 내용 모두 불러와서 추가하기 - Room 사용
//        val savedFoods = db!!.FoodsDao().getAll()
//        if (savedFoods.isNotEmpty()) {
//            // 생명주기상 onViewCreated 시 recyclerView에 계속 추가되는 오류 해결하기
//            if (foodsList.isEmpty()) {
//                foodsList.addAll(savedFoods)
//            }
//        }
        //어댑터, 아이템 클릭 : 아이템 삭제
        adapter = RecyclerViewAdapter(foodsList)

        adapter?.setItemClickListener(object : RecyclerViewAdapter.ItemClickListener {

            override fun onDeleteClick(position: Int) {
                /**
                 * 콜백 받음
                 */
                val foods = foodsList[position]
                db?.FoodsDao()?.delete(foods = foods) //DB에서 삭제
                foodsList.removeAt(position) //리스트에서 삭제
                adapter?.notifyDataSetChanged() //리스트뷰 갱신
                /**
                 * Firebase 데이터 지우기
                 */
                deleteFirebaseData(foods)
            }

            private fun deleteFirebaseData(foods: Foods) {
                if (::mySnapShot.isInitialized) {
                    val foodsSnapShot = mySnapShot.child("Foods")

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


        //버튼 클릭시 ChartActivity로 이통
        initChartBtn(binding)

    }


    private fun initChartBtn(binding: FragmentHistoryBinding?) {
        binding?.btnChart?.setOnClickListener {
            val food = foodsList.map { s -> s.name }.toCollection(ArrayList())
            val bundle = Bundle().apply {
                putStringArrayList("foodsList", foodsList.map { s -> s.name }.toCollection(ArrayList<String>()))
            }
            findNavController().navigate(R.id.action_historyFragment_to_chartFragment, bundle)
        }
    }


    private fun initFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //late init
                mySnapShot = snapshot
                getFoodList()
                initAdapter()
            }

            private fun initAdapter() {
                adapter = RecyclerViewAdapter(foodsList)

                adapter?.setItemClickListener(object : RecyclerViewAdapter.ItemClickListener {

                    override fun onDeleteClick(position: Int) {
                        /**
                         * 콜백 받음
                         */
                        val foods = foodsList[position]
                        db?.FoodsDao()?.delete(foods = foods) //DB에서 삭제
                        foodsList.removeAt(position) //리스트에서 삭제
                        adapter?.notifyDataSetChanged() //리스트뷰 갱신
                        /**
                         * Firebase 데이터 지우기
                         */
                        deleteFirebaseData(foods)
                    }

                    private fun deleteFirebaseData(foods: Foods) {
                        if (::mySnapShot.isInitialized) {
                            val foodsSnapShot = mySnapShot.child("Foods")

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

                binding?.mRecyclerView?.adapter = adapter
            }


            private fun getFoodList() {
                val savedFoodsSnapShot = mySnapShot.child("Foods").children
                if (foodsList.isEmpty()) {
                    for (savedFoodSnapshot in savedFoodsSnapShot) {
                        val savedFood = savedFoodSnapshot.getValue(Foods::class.java)
                        foodsList.add(savedFood!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}