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
    val sensorType: String,
    val timestamp: Long,
    val values: List<Float>
)


class SensorDataTypeConverters {

    @TypeConverter
    fun fromValuesList(values: List<Float>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Float>>() {}.type
        return gson.toJson(values, type)
    }

    @TypeConverter
    fun toValuesList(valuesString: String): List<Float> {
        val gson = Gson()
        val type = object : TypeToken<List<Float>>() {}.type
        return gson.fromJson(valuesString, type)
    }
}