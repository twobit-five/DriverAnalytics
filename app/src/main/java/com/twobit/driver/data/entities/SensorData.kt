package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "sensor_data")
@TypeConverters(SensorDataTypeConverters::class)
data class SensorData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val sensorType: String,
    val sensorCategory: String,
    val measurementName: String,
    val values: List<Float>,
    val unitOfMeasurement: String,
    val accuracy: Int
) {
}

class SensorDataTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromValuesList(values: List<Float>): String {
        val type = object : TypeToken<List<Float>>() {}.type
        return gson.toJson(values, type)
    }

    @TypeConverter
    fun toValuesList(valuesString: String): List<Float> {
        val type = object : TypeToken<List<Float>>() {}.type
        return gson.fromJson(valuesString, type)
    }
}