package com.example.kotlinproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.repository.FoodsRepository

class MyViewModel : ViewModel() {
    private val repository = FoodsRepository()

    private val _foodList = MutableLiveData<ArrayList<Foods>>()

    val foodList : LiveData<ArrayList<Foods>> get() = _foodList

    /**
     * viewModel 과 Model이 연결되는 곳
     */
    init {
        repository.observeFoods(_foodList)
    }

    fun deleteFood(foods: Foods) {
        repository.deleteFoods(foods)
    }

    fun addFirebaseData(food: Foods) {
        repository.addFirebaseData(food)
    }
}

