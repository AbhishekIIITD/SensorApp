package com.example.ass3

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OrientationData::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract val Dao: OrientationDao


}