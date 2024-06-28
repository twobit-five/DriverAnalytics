package com.twobit.driver.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.twobit.driver.data.dao.*
import com.twobit.driver.data.entities.*

@Database(
    entities = [
        Obd2Data::class,
        ImuData::class,
        GpsData::class,
        PhoneSensorData::class,
        LocationData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun obd2DataDao(): Obd2DataDao
    abstract fun imuDataDao(): ImuDataDao
    abstract fun gpsDataDao(): GpsDataDao
    abstract fun phoneSensorDataDao(): PhoneSensorDataDao
    abstract fun locationDataDao(): LocationDataDao
}
