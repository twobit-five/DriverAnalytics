package com.twobit.driver.data.repository

import com.twobit.driver.data.dao.Obd2DataDao
import com.twobit.driver.data.entities.Obd2Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Obd2DataRepository(private val obd2DataDao: Obd2DataDao) {
    suspend fun insert(obd2Data: Obd2Data) {
        withContext(Dispatchers.IO) {
            obd2DataDao.insert(obd2Data)
        }
    }

    suspend fun getNonUploaded(): List<Obd2Data> {
        return withContext(Dispatchers.IO) {
            obd2DataDao.getNonUploaded()
        }
    }

    suspend fun markAsUploaded(ids: List<Int>) {
        withContext(Dispatchers.IO) {
            obd2DataDao.markAsUploaded(ids)
        }
    }
}
