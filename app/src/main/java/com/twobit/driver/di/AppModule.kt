package com.twobit.driver.di

import android.app.Application
import android.content.Context
import androidx.room.Room

import com.twobit.driver.data.database.AppDatabase
import com.twobit.driver.data.repository.Repository
import com.twobit.driver.domain.bluetooth.BluetoothBroadcastReceiver
import com.twobit.driver.domain.sensors.AmbientTemperatureSensor
import com.twobit.driver.domain.sensors.BarometerSensor
import com.twobit.driver.domain.sensors.GravitySensor
import com.twobit.driver.domain.sensors.GyroscopeSensor
import com.twobit.driver.domain.sensors.LightSensor
import com.twobit.driver.domain.sensors.LinearAccelerationSensor
import com.twobit.driver.domain.sensors.MagnetometerSensor
import com.twobit.driver.domain.sensors.MeasurableSensor
import com.twobit.driver.domain.sensors.RelativeHumiditySensor
import com.twobit.driver.settings.AppSettingsSerializer
import com.twobit.driver.settings.SettingsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LightSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccelerometerSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GyroscopeSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MagnetometerSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BarometerSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GravitySensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeadingSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AmbientTemperatureSensorQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RelativeHumiditySensorQualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }


    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) : AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database",
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepository(db: AppDatabase): Repository {
        return Repository(db.sensorDataDao(), db.locationDataDao(), db.eventDao())
    }

    @Provides
    @Singleton
    fun providesBluetoothBroadcastReceiver(): BluetoothBroadcastReceiver {
        return BluetoothBroadcastReceiver()
    }

    @Provides
    @Singleton
    fun provideAppSettingsSerializer(): AppSettingsSerializer {
        return AppSettingsSerializer
    }

    @Provides
    @Singleton
    fun provideSettingsManager(
        context: Context,
        appSettingsSerializer: AppSettingsSerializer
    ): SettingsManager {
        return SettingsManager(context, appSettingsSerializer)
    }


    @Provides
    @LightSensorQualifier
    fun provideLightSensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return LightSensor(context, repository)
    }


    @Provides
    @AccelerometerSensorQualifier
    fun provideAccelerometerSensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return LinearAccelerationSensor(context, repository)
    }


    @Provides
    @GyroscopeSensorQualifier
    fun provideGyroscopeSensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return GyroscopeSensor(context, repository)
    }

    @Provides
    @MagnetometerSensorQualifier
    fun provideMagnetometerSensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return MagnetometerSensor(context, repository)
    }

    @Provides
    @BarometerSensorQualifier
    fun provideBarometerSensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return BarometerSensor(context, repository)
    }

    @Provides
    @GravitySensorQualifier
    fun provideGravitySensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return GravitySensor(context, repository)
    }

    @Provides
    @AmbientTemperatureSensorQualifier
    fun provideAmbientTemperatureSensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return AmbientTemperatureSensor(context, repository)
    }

    @Provides
    @RelativeHumiditySensorQualifier
    fun provideRelativeHumiditySensor(
        context: Context,
        repository: Repository
    ): MeasurableSensor {
        return RelativeHumiditySensor(context, repository)
    }
}