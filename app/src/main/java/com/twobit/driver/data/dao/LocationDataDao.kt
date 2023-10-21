package com.twobit.driver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.twobit.driver.data.entities.LocationData

@Dao
interface LocationDataDao {

    @Insert
    suspend fun insert(locationData: LocationData)
}
