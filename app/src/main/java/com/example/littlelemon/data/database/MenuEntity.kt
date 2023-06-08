package com.example.littlelemon.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.littlelemon.data.database.AppDatabase.Companion.MENU_TABLE

@Entity(tableName = MENU_TABLE)
data class MenuEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)
