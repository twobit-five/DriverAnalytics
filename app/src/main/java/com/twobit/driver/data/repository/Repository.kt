package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.LocationDataDao
import com.twobit.driver.data.dao.SensorDataDao
import com.twobit.driver.data.entities.LocationData
import com.twobit.driver.data.entities.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val dao: SensorDataDao,
    private val locationDataDao: LocationDataDao
) {

    // Function to insert sensor data into the database
    suspend fun insertSensorData(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            dao.insert(sensorData)
        }
    }

    suspend fun insertLocationData(locationData: LocationData) {
        withContext(Dispatchers.IO) {
            locationDataDao.insert(locationData)
        }
    }
}
