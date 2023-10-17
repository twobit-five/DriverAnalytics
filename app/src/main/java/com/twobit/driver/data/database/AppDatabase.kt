package com.twobit.driver.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.twobit.driver.data.dao.SensorDataDao
import com.twobit.driver.data.entities.SensorData

@Database(entities = [SensorData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sensorDataDao(): SensorDataDao
}
