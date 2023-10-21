package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.twobit.driver.data.entities.SensorData

@Dao
interface SensorDataDao {

    @Insert
    suspend fun insert(sensorData: SensorData)
}