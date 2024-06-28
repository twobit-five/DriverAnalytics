package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.LocationDataDao
import com.twobit.driver.data.entities.LocationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationDataRepository(private val locationDataDao: LocationDataDao) {
    suspend fun insert(locationData: LocationData) {
        withContext(Dispatchers.IO) {
            locationDataDao.insert(locationData)
        }
    }
}
