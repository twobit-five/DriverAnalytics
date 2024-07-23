package com.twobit.driver.domain.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.twobit.driver.data.entities.PhoneSensorData
import com.twobit.driver.data.repository.PhoneSensorDataRepository
import com.twobit.driver.domain.mqtt.HiveMQHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SensorDataUploadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val phoneSensorDataRepository: PhoneSensorDataRepository,
    private val hiveMQHelper: HiveMQHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.i("SensorDataUploadWorker", "Worker started")
        try {
            val nonUploadedData = phoneSensorDataRepository.getNonUploaded()
            Log.i("SensorDataUploadWorker", "Found ${nonUploadedData.size} records to upload")
            for (data in nonUploadedData) {
                uploadDataToMQTT(data)
                phoneSensorDataRepository.markAsUploaded(listOf(data.id))
            }
            Log.i("SensorDataUploadWorker", "Worker completed successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e("SensorDataUploadWorker", "Worker failed: ${e.message}", e)
            Result.retry()
        }
    }

    private fun uploadDataToMQTT(data: PhoneSensorData) {
        val payload = """
            {
                "timestamp": ${data.timestamp},
                "readLatency": ${data.readLatency},
                "accelerometerX": ${data.accelerometerX},
                "accelerometerY": ${data.accelerometerY},
                "accelerometerZ": ${data.accelerometerZ},
                "gyroscopeX": ${data.gyroscopeX},
                "gyroscopeY": ${data.gyroscopeY},
                "gyroscopeZ": ${data.gyroscopeZ},
                "magnetometerX": ${data.magnetometerX},
                "magnetometerY": ${data.magnetometerY},
                "magnetometerZ": ${data.magnetometerZ}
            }
        """.trimIndent()
        hiveMQHelper.connectAndPublish("sensor/data", payload)
    }
}
