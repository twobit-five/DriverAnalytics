package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.PhoneSensorData
import kotlinx.coroutines.flow.Flow

@Dao
interface PhoneSensorDataDao {
    @Insert
    suspend fun insert(phoneSensorData: PhoneSensorData)

    @Query("SELECT * FROM phone_sensor_data WHERE isUploaded = 0")
    suspend fun getNonUploaded(): List<PhoneSensorData>

    @Query("UPDATE phone_sensor_data SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<Int>)

    @Query("SELECT * FROM phone_sensor_data ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestData(): PhoneSensorData?

    @Query("SELECT * FROM phone_sensor_data ORDER BY timestamp DESC LIMIT 1")
    fun getLatestDataFlow(): Flow<PhoneSensorData?>
}
