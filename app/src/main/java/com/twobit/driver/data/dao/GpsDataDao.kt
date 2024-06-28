package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.GpsData

@Dao
interface GpsDataDao {
    @Insert
    suspend fun insert(gpsData: GpsData)

    @Query("SELECT * FROM gps_data WHERE isUploaded = 0")
    suspend fun getNonUploaded(): List<GpsData>

    @Query("UPDATE gps_data SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<Int>)

    @Query("SELECT * FROM gps_data ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestData(): GpsData?
}
