package com.twobit.driver.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.twobit.driver.DriversAnalyticsApp
import com.twobit.driver.domain.mqtt.HiveMQHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideHiveMQHelper(): HiveMQHelper {
        val serverUri = "2d150d82a5454c8584b4546ae4246680.s1.eu.hivemq.cloud"
        val clientId = "deviceID"
        val port = 8883
        val username = "publisher"
        val password = "ZDG2i:HnX_QAdda"
        return HiveMQHelper(serverUri, clientId, port, username, password)
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context,
        workerFactory: HiltWorkerFactory
    ): WorkManager {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(context, config)
        return WorkManager.getInstance(context)
    }
}
