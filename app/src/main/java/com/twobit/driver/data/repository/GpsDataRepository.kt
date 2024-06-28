package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.GpsDataDao
import com.twobit.driver.data.entities.GpsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GpsDataRepository(private val gpsDataDao: GpsDataDao) {
    suspend fun insert(gpsData: GpsData) {
        withContext(Dispatchers.IO) {
            gpsDataDao.insert(gpsData)
        }
    }

    suspend fun getNonUploaded(): List<GpsData> {
        return withContext(Dispatchers.IO) {
            gpsDataDao.getNonUploaded()
        }
    }

    suspend fun markAsUploaded(ids: List<Int>) {
        withContext(Dispatchers.IO) {
            gpsDataDao.markAsUploaded(ids)
        }
    }
}
