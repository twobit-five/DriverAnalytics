package com.twobit.driver.domain.sensors

abstract class MeasurableSensor(
    protected val sensorType: Int,
    protected val measurementName: String,
    protected val unitOfMeasurement: String,
) {

    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    abstract val doesSensorExist: Boolean

    abstract fun startListening()
    abstract fun stopListening()

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValuesChanged = listener
    }
}