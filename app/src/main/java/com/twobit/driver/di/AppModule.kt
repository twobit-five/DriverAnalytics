package com.twobit.driver.di

import android.app.Application
import android.content.Context
import androidx.room.Room

import com.twobit.driver.data.database.AppDatabase
import com.twobit.driver.data.repository.SensorDataRepository
import com.twobit.driver.domain.sensors.AccelerometerSensor
import com.twobit.driver.domain.sensors.AmbientTemperatureSensor
import com.twobit.driver.domain.sensors.BarometerSensor
import com.twobit.driver.domain.sensors.GravitySensor
import com.twobit.driver.domain.sensors.GyroscopeSensor
import com.twobit.driver.domain.sensors.HeadingSensor
import com.twobit.driver.domain.sensors.LightSensor
import com.twobit.driver.domain.sensors.MagnetometerSensor
import com.twobit.driver.domain.sensors.MeasurableSensor
import com.twobit.driver.domain.sensors.RelativeHumiditySensor
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
    fun providesRepository(db: AppDatabase): SensorDataRepository {
        return SensorDataRepository(db.sensorDataDao())
    }


    @Provides
    @LightSensorQualifier
    fun provideLightSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return LightSensor(context, repository)
    }


    @Provides
    @AccelerometerSensorQualifier
    fun provideAccelerometerSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return AccelerometerSensor(context, repository)
    }


    @Provides
    @GyroscopeSensorQualifier
    fun provideGyroscopeSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return GyroscopeSensor(context, repository)
    }

    @Provides
    @MagnetometerSensorQualifier
    fun provideMagnetometerSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return MagnetometerSensor(context, repository)
    }

    @Provides
    @BarometerSensorQualifier
    fun provideBarometerSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return BarometerSensor(context, repository)
    }

    @Provides
    @GravitySensorQualifier
    fun provideGravitySensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return GravitySensor(context, repository)
    }

    @Provides
    @HeadingSensorQualifier
    fun provideHeadingSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return HeadingSensor(context, repository)
    }

    @Provides
    @AmbientTemperatureSensorQualifier
    fun provideAmbientTemperatureSensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return AmbientTemperatureSensor(context, repository)
    }

    @Provides
    @RelativeHumiditySensorQualifier
    fun provideRelativeHumiditySensor(
        context: Context,
        repository: SensorDataRepository
    ): MeasurableSensor {
        return RelativeHumiditySensor(context, repository)
    }
}