package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.PhoneSensorDataDao
import com.twobit.driver.data.entities.PhoneSensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PhoneSensorDataRepository(private val phoneSensorDataDao: PhoneSensorDataDao) {
    suspend fun insert(phoneSensorData: PhoneSensorData) {
        withContext(Dispatchers.IO) {
            phoneSensorDataDao.insert(phoneSensorData)
        }
    }

    suspend fun getNonUploaded(): List<PhoneSensorData> {
        return withContext(Dispatchers.IO) {
            phoneSensorDataDao.getNonUploaded()
        }
    }

    suspend fun markAsUploaded(ids: List<Int>) {
        withContext(Dispatchers.IO) {
            phoneSensorDataDao.markAsUploaded(ids)
        }
    }

    suspend fun getLatestData(): PhoneSensorData? {
        return withContext(Dispatchers.IO) {
            phoneSensorDataDao.getLatestData()
        }
    }

    fun getLatestDataFlow(): Flow<PhoneSensorData?> {
        return phoneSensorDataDao.getLatestDataFlow()
    }
}
