package com.twobit.driver.domain.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.twobit.driver.data.entities.SensorData
import com.twobit.driver.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String?,
    sensorType: Int,
    private val sensorCategory: String,
    measurementName: String,
    unitOfMeasurement: String,
    private val repository: Repository,
): MeasurableSensor(sensorType, measurementName, unitOfMeasurement), SensorEventListener {

    private val TAG = "AndroidSensor"

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
        if(!doesSensorExist) {
            Log.d(TAG, "Sensor does not exist: $sensorType")
            return
        }

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
        Log.d(TAG, "Started listening to sensor: $sensorType")
    }

    override fun stopListening() {
        if(!doesSensorExist || !::sensorManager.isInitialized) {
            return
        }

        sensorManager.unregisterListener(this)
        Log.d(TAG, "Stopped listening to sensor: $sensorType")
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
                values = event.values.toList(),
                accuracy = event.accuracy,
                sensorCategory = sensorCategory,
                measurementName = measurementName,
                unitOfMeasurement = unitOfMeasurement
            )
        }

        // Called too often
        //Log.d(TAG, "Sensor data: $sensorData")

        // Save it to the database
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (sensorData != null) {
                    repository.insertSensorData(sensorData)
                }
            } catch (e: Exception) {
                // Log or handle database insertion error
                Log.e(TAG, "Error inserting sensor data into database", e)
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.e(TAG, "On Accuracy Changed: $sensor, $accuracy")
    }
}