package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gps_data")
data class GpsData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val readLatency: Long,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double?,
    val isUploaded: Boolean = false
)
