package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_sensor_data")
data class RemoteSensorData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val readLatency: Long,
    val data: String,
    val isUploaded: Boolean = false
)
