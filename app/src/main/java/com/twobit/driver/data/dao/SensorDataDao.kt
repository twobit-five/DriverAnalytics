package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.SensorData

@Dao
interface SensorDataDao {
    @Insert
    suspend fun insert(sensorData: SensorData)

    @Query("SELECT * FROM sensor_data WHERE timestamp >= :startTime AND timestamp <= :endTime")
    suspend fun getSensorDataWithinTimeRange(startTime: Long, endTime: Long): List<SensorData>
}