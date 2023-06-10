package com.example.littlelemon.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_table")
    fun getMenuItems(): LiveData<List<MenuEntity>>

    @Insert
    fun addMenuItems(vararg menuItems: MenuEntity)

    @Query("SELECT (SELECT COUNT(*) FROM menu_table) == 0")
    fun isEmpty(): Boolean
}