package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.ImuData

@Dao
interface ImuDataDao {
    @Insert
    suspend fun insert(imuData: ImuData)

    @Query("SELECT * FROM imu_data WHERE isUploaded = 0")
    suspend fun getNonUploaded(): List<ImuData>

    @Query("UPDATE imu_data SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<Int>)

    @Query("SELECT * FROM imu_data ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestData(): ImuData?
}
