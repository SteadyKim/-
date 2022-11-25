package com.example.kotlinproject.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlinproject.db.entity.Foods
import com.google.firebase.database.*

class FoodsRepository {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()

    fun observeFoods(foodsList: MutableLiveData<ArrayList<Foods>>) {

        databaseReference.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tmpFoodsList = ArrayList<Foods>()
                val savedFoodsSnapShot = snapshot.child("Foods").children
                for (savedFoodSnapshot in savedFoodsSnapShot) {
                    val savedFood = savedFoodSnapshot.getValue(Foods::class.java)
                    println("savedFood?.name = ${savedFood?.name}")
                    tmpFoodsList.add(savedFood!!)
                }
                foodsList.value = tmpFoodsList
                Log.d("FoodsRepo:", foodsList.value?.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}