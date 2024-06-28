package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "obd2_data")
data class Obd2Data(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val readLatency: Long,
    val rpm: Int?,
    val speed: Int?,
    val fuelLevel: Float?,
    val engineLoad: Float?,
    val coolantTemperature: Float?,
    val isUploaded: Boolean = false
)
