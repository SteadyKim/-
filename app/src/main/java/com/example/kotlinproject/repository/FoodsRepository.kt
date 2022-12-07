package com.example.kotlinproject.repository


import androidx.lifecycle.MutableLiveData
import com.example.kotlinproject.db.entity.Foods
import com.google.firebase.database.*

class FoodsRepository {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()
    lateinit var mySnapShot : DataSnapshot

    fun observeFoods(foodsList: MutableLiveData<ArrayList<Foods>>) {

        databaseReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // SnapShot 초기화
                mySnapShot = snapshot

                val tmpFoodsList = ArrayList<Foods>()
                val savedFoodsSnapShot = snapshot.child("Foods").children
                for (savedFoodSnapshot in savedFoodsSnapShot) {
                    val savedFood = savedFoodSnapshot.getValue(Foods::class.java)
                    tmpFoodsList.add(savedFood!!)
                }
                foodsList.value = tmpFoodsList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteFoods(foods: Foods) {
        val foodsSnapShot = mySnapShot.child("Foods")
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

    fun addFirebaseData(food: Foods) {
        databaseReference.child("Foods").push().setValue(food)
    }
}