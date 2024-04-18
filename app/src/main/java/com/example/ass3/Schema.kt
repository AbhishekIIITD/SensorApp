package com.example.ass3

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class OrientationData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pitch: Double,
    val roll: Double,
    val yaw: Double,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface OrientationDao {
    @Insert
    suspend fun insert(orientationData: OrientationData)

    @Query("DELETE FROM OrientationData")
    suspend fun deleteAll()

    @Query("SELECT * FROM OrientationData")
    fun getAll(): Flow<List<OrientationData>>
}
