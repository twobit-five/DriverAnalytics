package com.twobit.driver.domain.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
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
): MeasurableSensor(sensorType, measurementName, unitOfMeasurement), SensorEventListener {

    private val TAG = "AndroidSensor"

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    private var lastSensorData: List<Float>? = null

    override val doesSensorExist: Boolean
        get() = sensorFeature?.let { feature ->
            context.packageManager.hasSystemFeature(feature)
        } ?: run {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val sensor = sensorManager.getDefaultSensor(sensorType)
            sensor != null
        }

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
            lastSensorData = event.values.toList()
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.e(TAG, "On Accuracy Changed: $sensor, $accuracy")
    }

    fun getCurrentData(): List<Float>? {
        return lastSensorData
    }
}
