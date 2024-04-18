package com.example.ass3

data class SensorData(
    val pitch: Double,
    val roll: Double,
    val yaw: Double,
    val timestamp: Long = System.currentTimeMillis()
)
