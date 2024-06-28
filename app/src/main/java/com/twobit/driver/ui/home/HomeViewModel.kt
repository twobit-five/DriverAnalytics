package com.twobit.driver.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twobit.driver.domain.sensors.MeasurableSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class HomeViewModel @Inject constructor(
    //@LightSensorQualifier
    //private val lightSensor: MeasurableSensor
): ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }
}