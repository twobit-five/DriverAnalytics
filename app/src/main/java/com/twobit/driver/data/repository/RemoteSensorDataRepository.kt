package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.RemoteDataDao
import com.twobit.driver.data.entities.RemoteSensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSensorDataRepository(private val remoteDataDao: RemoteDataDao) {
    suspend fun insert(remoteSensorData: RemoteSensorData) {
        withContext(Dispatchers.IO) {
            remoteDataDao.insert(remoteSensorData)
        }
    }

    suspend fun getNonUploaded(): List<RemoteSensorData> {
        return withContext(Dispatchers.IO) {
            remoteDataDao.getNonUploaded()
        }
    }

    suspend fun markAsUploaded(ids: List<Int>) {
        withContext(Dispatchers.IO) {
            remoteDataDao.markAsUploaded(ids)
        }
    }
}
