package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.RemoteSensorData

@Dao
interface RemoteDataDao {
    @Insert
    suspend fun insert(remoteSensorData: RemoteSensorData)

    @Query("SELECT * FROM remote_sensor_data WHERE isUploaded = 0")
    suspend fun getNonUploaded(): List<RemoteSensorData>

    @Query("UPDATE remote_sensor_data SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<Int>)
}
