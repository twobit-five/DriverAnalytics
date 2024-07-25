package com.twobit.driver.domain.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GravitySensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_GRAVITY,
    sensorCategory = "MOTION",
    measurementName = "Gravity",
    unitOfMeasurement = "m/s^2"
)

class GyroscopeSensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE,
    sensorCategory = "MOTION",
    measurementName = "Gyroscope",
    unitOfMeasurement = "rad/s"
)

class LightSensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT,
    sensorCategory = "ENVIRONMENT",
    measurementName = "Light",
    unitOfMeasurement = "lx"
)

class LinearAccelerationSensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_LINEAR_ACCELERATION,
    sensorCategory = "MOTION",
    measurementName = "Acceleration",
    unitOfMeasurement = "m/s^2"
)

class MagnetometerSensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    sensorCategory = "POSITION",
    measurementName = "Magnetic Field",
    unitOfMeasurement = "μT"
)
class AccelerometerSensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER,
    sensorCategory = "MOTION",
    measurementName = "Acceleration",
    unitOfMeasurement = "m/s^2"
)

class ProximitySensor @Inject constructor(
    @ApplicationContext context: Context
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_PROXIMITY,
    sensorType = Sensor.TYPE_PROXIMITY,
    sensorCategory = "ENVIRONMENT",
    measurementName = "Proximity",
    unitOfMeasurement = "cm"
)