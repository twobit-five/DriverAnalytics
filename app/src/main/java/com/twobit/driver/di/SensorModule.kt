package com.twobit.driver.di

import android.content.Context
import com.twobit.driver.domain.sensors.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

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
annotation class GravitySensorQualifier

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @AccelerometerSensorQualifier
    fun provideAccelerometerSensor(
        @ApplicationContext context: Context,
    ): MeasurableSensor {
        return LinearAccelerationSensor(context)
    }

    @Provides
    @GyroscopeSensorQualifier
    fun provideGyroscopeSensor(
        @ApplicationContext context: Context,
    ): MeasurableSensor {
        return GyroscopeSensor(context)
    }

    @Provides
    @MagnetometerSensorQualifier
    fun provideMagnetometerSensor(
        @ApplicationContext context: Context,
    ): MeasurableSensor {
        return MagnetometerSensor(context)
    }

    @Provides
    @GravitySensorQualifier
    fun provideGravitySensor(
        @ApplicationContext context: Context,
    ): MeasurableSensor {
        return GravitySensor(context)
    }
}
