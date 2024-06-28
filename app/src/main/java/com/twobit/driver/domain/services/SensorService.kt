package com.twobit.driver.domain.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.twobit.driver.data.entities.PhoneSensorData
import com.twobit.driver.domain.sensors.*
//import com.twobit.driver.domain.workers.MqttPublisherWorker
import com.twobit.driver.data.repository.PhoneSensorDataRepository
import com.twobit.driver.data.repository.LocationDataRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SensorService : Service() {

    @Inject
    lateinit var accelerometerSensor: LinearAccelerationSensor
    @Inject
    lateinit var gravitySensor: GravitySensor
    @Inject
    lateinit var gyroscopeSensor: GyroscopeSensor
    @Inject
    lateinit var magnetometerSensor: MagnetometerSensor

    @Inject
    lateinit var phoneSensorDataRepository: PhoneSensorDataRepository
    @Inject
    lateinit var locationDataRepository: LocationDataRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    //private lateinit var workManager: WorkManager

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

        startForeground(1, notification)

        //workManager = WorkManager.getInstance(this)
        //val request = PeriodicWorkRequestBuilder<MqttPublisherWorker>(15, TimeUnit.MINUTES)
        //    .build()
        //WorkManager.getInstance(this@SensorService).enqueue(request)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startListeningToSensors()


        serviceScope.launch {
            while (isActive) {
                val phoneData = collectAndStorePhoneSensorData()
                delay(100)

            }
        }


        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        stopListeningToSensors()

        serviceScope.cancel()
    }

    private fun startListeningToSensors() {
        accelerometerSensor.startListening()
        gravitySensor.startListening()
        gyroscopeSensor.startListening()
        magnetometerSensor.startListening()
    }

    private fun stopListeningToSensors() {
        accelerometerSensor.stopListening()
        gravitySensor.stopListening()
        gyroscopeSensor.stopListening()
        magnetometerSensor.stopListening()
    }

    private suspend fun collectAndStorePhoneSensorData(): PhoneSensorData {
        val timestamp = System.currentTimeMillis()

        val accelerometerData = accelerometerSensor.getCurrentData()
        val gyroscopeData = gyroscopeSensor.getCurrentData()
        val magnetometerData = magnetometerSensor.getCurrentData()

        val readLatency = System.currentTimeMillis() - timestamp

        val phoneSensorData = PhoneSensorData(
            id = 0,
            timestamp = timestamp,
            readLatency = readLatency, // Add your latency calculation logic here
            accelerometerX = accelerometerData?.get(0),
            accelerometerY = accelerometerData?.get(1),
            accelerometerZ = accelerometerData?.get(2),
            gyroscopeX = gyroscopeData?.get(0),
            gyroscopeY = gyroscopeData?.get(1),
            gyroscopeZ = gyroscopeData?.get(2),
            magnetometerX = magnetometerData?.get(0),
            magnetometerY = magnetometerData?.get(1),
            magnetometerZ = magnetometerData?.get(2),
            isUploaded = false
        )

        //TODO separate the collecting and storing functionality
        phoneSensorDataRepository.insert(phoneSensorData)
        return phoneSensorData
    }
/*
    private fun enqueueUploadWorker() {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<MqttPublisherWorker>()
            .setInputData(workDataOf("repository" to phoneSensorDataRepository))
            .build()
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }
*/
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
