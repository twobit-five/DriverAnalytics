// RepositoryModule.kt
package com.twobit.driver.di

import com.twobit.driver.data.database.AppDatabase
import com.twobit.driver.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideObd2DataRepository(db: AppDatabase): Obd2DataRepository {
        return Obd2DataRepository(db.obd2DataDao())
    }

    @Provides
    @Singleton
    fun provideRemoteSensorDataRepository(db: AppDatabase): RemoteSensorDataRepository {
        return RemoteSensorDataRepository(db.remoteSensorDataDao())
    }


    @Provides
    @Singleton
    fun providePhoneSensorDataRepository(db: AppDatabase): PhoneSensorDataRepository {
        return PhoneSensorDataRepository(db.phoneSensorDataDao())
    }

    @Provides
    @Singleton
    fun provideLocationDataRepository(db: AppDatabase): LocationDataRepository {
        return LocationDataRepository(db.locationDataDao())
    }
}
