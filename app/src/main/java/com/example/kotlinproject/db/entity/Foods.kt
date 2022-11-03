package com.example.kotlinproject.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_foods")
data class Foods (
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    var date: String
)