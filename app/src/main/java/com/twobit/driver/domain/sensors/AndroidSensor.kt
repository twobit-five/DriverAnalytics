package com.twobit.driver.domain.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.twobit.driver.data.entities.SensorData
import com.twobit.driver.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String?,
    sensorType: Int,
    private val repository: Repository
): MeasurableSensor(sensorType), SensorEventListener {

    override val doesSensorExist: Boolean
        get() = sensorFeature?.let { feature ->
            context.packageManager.hasSystemFeature(feature)
        } ?: run {
            // If sensorFeature is null, check for the sensor using SensorManager
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensor = sensorManager.getDefaultSensor(sensorType)
            sensor != null
        }

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun startListening() {
        if(!doesSensorExist) return

        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }


    override fun stopListening() {
        if(!doesSensorExist || !::sensorManager.isInitialized) {
            return
        }

        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!doesSensorExist) {
            return
        }

        if(event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }

        // Create a SensorData object
        val sensorData = event?.sensor?.let {
            SensorData(
                sensorType = it.stringType,
                timestamp = System.currentTimeMillis(),
                values = event.values.toList()
            )
        }
        // Save it to the database
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (sensorData != null) {
                    repository.insertSensorData(sensorData)
                }
            } catch (e: Exception) {
                // Log or handle database insertion error
                e.printStackTrace()
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }
}