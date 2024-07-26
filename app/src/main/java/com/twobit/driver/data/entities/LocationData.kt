package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_data")
data class LocationData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val accuracy: Float,

    val bearing: Float,
    val bearingAccuracy: Float,
    val speed: Float,
    val speedAccuracy: Float,
    val isUploaded: Boolean = false
)