package com.twobit.driver.domain.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.twobit.driver.data.entities.LocationData
import com.twobit.driver.data.entities.PhoneSensorData
import com.twobit.driver.domain.sensors.*
import com.twobit.driver.data.repository.PhoneSensorDataRepository
import com.twobit.driver.data.repository.LocationDataRepository
import com.twobit.driver.domain.location.DefaultLocationClient
import com.twobit.driver.domain.location.LocationClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SensorService : Service() {
    private val TAG = "SensorService"

    @Inject
    lateinit var linerarAccelerometerSensor: LinearAccelerationSensor
    @Inject
    lateinit var gravitySensor: GravitySensor
    @Inject
    lateinit var gyroscopeSensor: GyroscopeSensor
    @Inject
    lateinit var magnetometerSensor: MagnetometerSensor
    @Inject
    lateinit var lightSensor: LightSensor
    @Inject
    lateinit var accelerometerSensor: AccelerometerSensor
    @Inject
    lateinit var proximitySensor: ProximitySensor

    @Inject
    lateinit var phoneSensorDataRepository: PhoneSensorDataRepository
    @Inject
    lateinit var locationDataRepository: LocationDataRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(intent: Intent?): IBinder? {
        return null  // Binding not supported
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification: Notification = NotificationCompat.Builder(this, "SensorServiceChannel")
            .setContentTitle("Sensor Service")
            .setContentText("Collecting sensor data...")
            .build()

        locationClient = DefaultLocationClient(applicationContext, LocationServices.getFusedLocationProviderClient(applicationContext))

        serviceScope.launch {
            locationClient.getLocationUpdates(100L).collect { location ->
                val locationData = LocationData(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    altitude = location.altitude,
                    bearing = location.bearing,
                    bearingAccuracy = location.bearingAccuracyDegrees,
                    speed = location.speed,
                    speedAccuracy = location.speedAccuracyMetersPerSecond,
                    timestamp = System.currentTimeMillis()
                )
                locationDataRepository.insert(locationData)
            }
        }

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //log the start of the service

        Log.e(TAG, "Starting SensorService")
        startListeningToSensors()
        locationClient = DefaultLocationClient(applicationContext, LocationServices.getFusedLocationProviderClient(applicationContext))

        serviceScope.launch {
            Log.e(TAG, "Starting coroutine")
            while (isActive) {
                val phoneSensorData = collectPhoneSensorData()
                phoneSensorDataRepository.insert(phoneSensorData)
                delay(100)
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "Stopping SensorService")

        stopListeningToSensors()

        serviceScope.cancel()
    }

    private fun startListeningToSensors() {
        Log.e(TAG, "Starting listening to sensors")
        linerarAccelerometerSensor.startListening()
        gravitySensor.startListening()
        gyroscopeSensor.startListening()
        magnetometerSensor.startListening()
        lightSensor.startListening()
        accelerometerSensor.startListening()
        proximitySensor.startListening()
    }

    private fun stopListeningToSensors() {
        Log.e(TAG, "Stopping listening to sensors")
        linerarAccelerometerSensor.stopListening()
        gravitySensor.stopListening()
        gyroscopeSensor.stopListening()
        magnetometerSensor.stopListening()
        lightSensor.stopListening()
        accelerometerSensor.stopListening()
        proximitySensor.stopListening()
    }

    private fun calculateHeading(accelerometerData: FloatArray, magnetometerData: FloatArray): Float {
        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData)
        SensorManager.getOrientation(rotationMatrix, orientationAngles)

        // orientationAngles[0] contains the azimuth in radians
        val azimuth = orientationAngles[0]
        // Convert azimuth to degrees
        var azimuthDegrees = Math.toDegrees(azimuth.toDouble()).toFloat()

        azimuthDegrees = (azimuthDegrees + 360) % 360

        return azimuthDegrees
    }

    private fun collectPhoneSensorData(): PhoneSensorData {
        val timestamp = System.currentTimeMillis()

        val linearAccelerometerData = linerarAccelerometerSensor.getCurrentData()
        val gyroscopeData = gyroscopeSensor.getCurrentData()
        val magnetometerData = magnetometerSensor.getCurrentData()
        val lightData = lightSensor.getCurrentData()
        val accelerometerData = accelerometerSensor.getCurrentData()
        val proximityData = proximitySensor.getCurrentData()

        // Calculate compass heading
        val heading = if (linearAccelerometerData != null && magnetometerData != null) {
            accelerometerData?.let { calculateHeading(it.toFloatArray(), magnetometerData.toFloatArray()) }
        } else {
            null
        }

        val readLatency = System.currentTimeMillis() - timestamp

        val phoneSensorData = PhoneSensorData(
            id = 0,
            timestamp = timestamp,
            readLatency = readLatency,
            accelerometerX = linearAccelerometerData?.get(0),
            accelerometerY = linearAccelerometerData?.get(1),
            accelerometerZ = linearAccelerometerData?.get(2),
            gyroscopeX = gyroscopeData?.get(0),
            gyroscopeY = gyroscopeData?.get(1),
            gyroscopeZ = gyroscopeData?.get(2),
            magnetometerX = magnetometerData?.get(0),
            magnetometerY = magnetometerData?.get(1),
            magnetometerZ = magnetometerData?.get(2),
            light = lightData?.get(0),
            compassHeading = heading,
            proximity = proximityData?.get(0),
            isUploaded = false
        )

        return phoneSensorData
    }

    private fun createNotificationChannel() {
    val serviceChannel = NotificationChannel(
        "SensorServiceChannel",
        "Sensor Service Channel",
        NotificationManager.IMPORTANCE_LOW
    )
    Log.e(TAG, "Creating notification channel")
    val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(serviceChannel)
}
}
