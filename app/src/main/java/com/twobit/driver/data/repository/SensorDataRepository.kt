package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.SensorDataDao
import com.twobit.driver.data.entities.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SensorDataRepository(
    private val dao: SensorDataDao
) {

    // Function to insert sensor data into the database
    suspend fun insertSensorData(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            dao.insert(sensorData)
        }
    }
}
