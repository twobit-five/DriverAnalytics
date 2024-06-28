package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.twobit.driver.data.entities.LocationData

@Dao
interface LocationDataDao {
    @Insert
    suspend fun insert(locationData: LocationData)

    @Query("SELECT * FROM location_data WHERE isUploaded = 0")
    suspend fun getNonUploaded(): List<LocationData>

    @Query("UPDATE location_data SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<Int>)

    @Query("SELECT * FROM location_data ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestData(): LocationData?
}