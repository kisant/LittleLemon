package com.example.littlelemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MenuEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        const val MENU_TABLE = "menu_table"
    }
}