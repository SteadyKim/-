package com.example.kotlinproject.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinproject.db.entity.Foods

@Dao
interface FoodsDao {
    @Query("SELECT * FROM tb_foods")
    fun getAll(): List<Foods>

    @Insert
    fun insertAll(vararg  foods: Foods)

    @Delete
    fun delete(foods: Foods)
}