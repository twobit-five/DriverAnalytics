package com.twobit.driver.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twobit.driver.di.LightSensorQualifier
import com.twobit.driver.domain.sensors.MeasurableSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @LightSensorQualifier
    private val lightSensor: MeasurableSensor
): ViewModel() {

    var isDark by mutableStateOf(false)

    // LiveData to hold the light sensor value
    private val _lightSensorValue = MutableLiveData<String>()
    val lightSensorValue: LiveData<String> get() = _lightSensorValue

    init {
        lightSensor.startListening()
        lightSensor.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
            isDark = lux < 60f
            _lightSensorValue.value = lux.toString()
        }
    }
}