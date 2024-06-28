package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imu_data")
data class ImuData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val readLatency: Long,
    val accelX: Float?,
    val accelY: Float?,
    val accelZ: Float?,
    val gyroX: Float?,
    val gyroY: Float?,
    val gyroZ: Float?,
    val magX: Float?,
    val magY: Float?,
    val magZ: Float?,
    val isUploaded: Boolean = false
)
