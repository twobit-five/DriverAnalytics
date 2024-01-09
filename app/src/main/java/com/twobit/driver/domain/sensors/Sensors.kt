package com.twobit.driver.domain.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import com.twobit.driver.data.repository.Repository
import com.twobit.driver.settings.SensorType
import javax.inject.Inject


// ENVIROMENT SENSORS
class LightSensor @Inject constructor(
    context: Context,
    repository: Repository
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT,
    sensorCategory = "ENVIRONMENT",
    measurementName = SensorType.LIGHT.name,
    unitOfMeasurement = "lx",
    repository = repository
)

class BarometerSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_BAROMETER,
    sensorType = Sensor.TYPE_PRESSURE,
    sensorCategory = "ENVIRONMENT",
    measurementName = SensorType.PRESSURE.name,
    unitOfMeasurement = "hPa", //hpa or mbar
    repository = repository
)

class AmbientTemperatureSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE,
    sensorType = Sensor.TYPE_AMBIENT_TEMPERATURE,
    sensorCategory = "ENVIRONMENT",
    measurementName = SensorType.AMBIENT_TEMPERATURE.name,
    unitOfMeasurement = "°C",
    repository = repository
)

class RelativeHumiditySensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY,
    sensorType = Sensor.TYPE_RELATIVE_HUMIDITY,
    sensorCategory = "ENVIRONMENT",
    measurementName = SensorType.RELATIVE_HUMIDITY.name,
    unitOfMeasurement = "%",
    repository = repository
)





// MOTION SENSORS
class LinearAccelerationSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_LINEAR_ACCELERATION,
    sensorCategory = "MOTION",
    measurementName = SensorType.ACCELERATION.name,
    unitOfMeasurement = "m/s^2",
    repository = repository
)

class GravitySensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_GRAVITY,
    sensorCategory = "MOTION",
    measurementName = SensorType.GRAVITY.name,
    unitOfMeasurement = "m/s^2",
    repository = repository
)

class GyroscopeSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE,
    sensorType = Sensor.TYPE_GYROSCOPE,
    sensorCategory = "MOTION",
    measurementName = SensorType.GYROSCOPE.name,
    unitOfMeasurement = "rad/s",
    repository = repository
)



//Position Sensors
class MagnetometerSensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    sensorCategory = "POSITION",
    measurementName = SensorType.MAGNETIC_FIELD.name,
    unitOfMeasurement = "μT",
    repository = repository
)

class ProximitySensor @Inject constructor(
    context: Context,
    repository: Repository
) : AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_PROXIMITY,
    sensorType = Sensor.TYPE_PROXIMITY,
    sensorCategory = "POSITION",
    measurementName = SensorType.PROXIMITY.name,
    unitOfMeasurement = "cm",
    repository = repository
)


