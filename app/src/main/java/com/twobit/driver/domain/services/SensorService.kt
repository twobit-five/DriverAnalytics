package com.twobit.driver.domain.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.twobit.driver.domain.sensors.AccelerometerSensor
import com.twobit.driver.domain.sensors.LightSensor
import com.twobit.driver.data.repository.SensorDataRepository
import com.twobit.driver.domain.sensors.AmbientTemperatureSensor
import com.twobit.driver.domain.sensors.BarometerSensor
import com.twobit.driver.domain.sensors.GravitySensor
import com.twobit.driver.domain.sensors.GyroscopeSensor
import com.twobit.driver.domain.sensors.HeadingSensor
import com.twobit.driver.domain.sensors.MagnetometerSensor
import com.twobit.driver.domain.sensors.RelativeHumiditySensor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class SensorService : Service() {

    @Inject
    lateinit var lightSensor: LightSensor
    @Inject
    lateinit var accelerometerSensor: AccelerometerSensor
    @Inject
    lateinit var gyroscopeSensor: GyroscopeSensor
    @Inject
    lateinit var magnetometerSensor: MagnetometerSensor
    @Inject
    lateinit var barometerSensor: BarometerSensor
    @Inject
    lateinit var gravitySensor: GravitySensor
    @Inject
    lateinit var headingSensor: HeadingSensor
    @Inject
    lateinit var ambientTemperatureSensor: AmbientTemperatureSensor
    @Inject
    lateinit var relativeHumiditySensor: RelativeHumiditySensor

    @Inject
    lateinit var sensorDataRepository: SensorDataRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null  // Binding not supported
    }

    override fun onCreate() {
        super.onCreate()

        // Create a notification channel and start the service in the foreground
        createNotificationChannel()
        val notification: Notification = NotificationCompat.Builder(this, "SensorServiceChannel")
            .setContentTitle("Sensor Service")
            .setContentText("Collecting sensor data...")
            // ... other notification settings ...
            .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start listening to sensor data
        lightSensor.startListening()
        accelerometerSensor.startListening()
        gyroscopeSensor.startListening()
        magnetometerSensor.startListening()
        barometerSensor.startListening()
        gravitySensor.startListening()
        headingSensor.startListening()
        ambientTemperatureSensor.startListening()
        relativeHumiditySensor.startListening()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop listening to sensor data
        lightSensor.stopListening()
        accelerometerSensor.stopListening()
        gyroscopeSensor.stopListening()
        magnetometerSensor.stopListening()
        barometerSensor.stopListening()
        gravitySensor.stopListening()
        headingSensor.stopListening()
        ambientTemperatureSensor.stopListening()
        relativeHumiditySensor.stopListening()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "SensorServiceChannel",
                "Sensor Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
