package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.ImuDataDao
import com.twobit.driver.data.entities.ImuData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImuDataRepository(private val imuDataDao: ImuDataDao) {
    suspend fun insert(imuData: ImuData) {
        withContext(Dispatchers.IO) {
            imuDataDao.insert(imuData)
        }
    }

    suspend fun getNonUploaded(): List<ImuData> {
        return withContext(Dispatchers.IO) {
            imuDataDao.getNonUploaded()
        }
    }

    suspend fun markAsUploaded(ids: List<Int>) {
        withContext(Dispatchers.IO) {
            imuDataDao.markAsUploaded(ids)
        }
    }
}
