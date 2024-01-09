package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.EventDao
import com.twobit.driver.data.dao.LocationDataDao
import com.twobit.driver.data.dao.SensorDataDao
import com.twobit.driver.data.entities.Event
import com.twobit.driver.data.entities.LocationData
import com.twobit.driver.data.entities.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val sensorDataDao: SensorDataDao,
    private val locationDataDao: LocationDataDao,
    private val eventDao: EventDao
) {

    // Function to insert sensor data into the database
    suspend fun insertSensorData(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            sensorDataDao.insert(sensorData)
        }
    }

    suspend fun insertLocationData(locationData: LocationData) {
        withContext(Dispatchers.IO) {
            locationDataDao.insert(locationData)
        }
    }

    suspend fun getSensorDataWithinTimeRange(startTime: Long, endTime: Long): List<SensorData> {
        return sensorDataDao.getSensorDataWithinTimeRange(startTime, endTime)
    }
    suspend fun insertEvent(event: Event) {
        withContext(Dispatchers.IO) {
            eventDao.insert(event)
        }
    }
}
