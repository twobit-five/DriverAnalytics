package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone_sensor_data")
data class PhoneSensorData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val readLatency: Long,
    val accelerometerX: Float?,
    val accelerometerY: Float?,
    val accelerometerZ: Float?,
    val gyroscopeX: Float?,
    val gyroscopeY: Float?,
    val gyroscopeZ: Float?,
    val magnetometerX: Float?,
    val magnetometerY: Float?,
    val magnetometerZ: Float?,
    val light: Float?,
    val compassHeading: Float?,
    val proximity: Float?,
    val isUploaded: Boolean = false
)
