package com.example.kotlinproject.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_foods")
data class Foods (
    @PrimaryKey val id: String = "",
    var name: String = "",
    var date: String = ""
)