package com.twobit.driver.domain.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import com.twobit.driver.data.repository.Repository
import javax.inject.Inject


class LightSensor @Inject constructor(
    context: Context,
    repository: Repository
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT,
    repository = repository
)


class AccelerometerSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ACCELEROMETER,
    repository = repository
)


class GyroscopeSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE,
    repository = repository
)

class MagnetometerSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    repository = repository
)

class BarometerSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_BAROMETER,
    sensorType = Sensor.TYPE_PRESSURE,
    repository = repository
)

class GravitySensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    //sensorFeature = PackageManager.FEATURE_SENSOR_GRAVITY,
    sensorFeature = null,
    sensorType = Sensor.TYPE_GRAVITY,
    repository = repository
)

class HeadingSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS,
    sensorType = Sensor.TYPE_ORIENTATION,
    repository = repository
)

class AmbientTemperatureSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE,
    repository = repository
)

class RelativeHumiditySensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY,
    repository = repository
)

