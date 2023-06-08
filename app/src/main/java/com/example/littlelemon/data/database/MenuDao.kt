package com.example.littlelemon.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_table")
    suspend fun getMenuItems(): LiveData<List<MenuEntity>>

    @Insert
    suspend fun addMenuItems(vararg menuItems: MenuEntity)

    @Query("SELECT (SELECT COUNT(*) FROM menu_table) == 0")
    suspend fun isEmpty(): Boolean
}