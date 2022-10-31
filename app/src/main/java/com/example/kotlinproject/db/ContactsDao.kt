package com.example.kotlinproject.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinproject.db.entity.Contacts

@Dao
interface ContactsDao {
    @Query("SELECT * FROM tb_contacts")
    fun getAll(): List<Contacts>

    @Insert
    fun insertAll(vararg  contacts: Contacts)

    @Delete
    fun delete(contacts: Contacts)
}