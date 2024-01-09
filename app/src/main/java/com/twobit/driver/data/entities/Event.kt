package com.twobit.driver.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "event")
@TypeConverters(SensorDataListTypeConverter::class)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startTime: Long,
    val endTime: Long,
    val eventType: String,
    val sensorDataList: List<SensorData>
)
class SensorDataListTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun fromSensorDataList(sensorDataList: List<SensorData>): String {
        val type = object : TypeToken<List<SensorData>>() {}.type
        return gson.toJson(sensorDataList, type)
    }

    @TypeConverter
    fun toSensorDataList(sensorDataListString: String): List<SensorData> {
        val type = object : TypeToken<List<SensorData>>() {}.type
        return gson.fromJson(sensorDataListString, type)
    }
}