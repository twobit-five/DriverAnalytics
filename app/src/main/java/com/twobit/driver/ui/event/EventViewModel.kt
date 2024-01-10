package com.twobit.driver.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobit.driver.data.entities.Event
import com.twobit.driver.data.repository.Repository
import com.twobit.driver.settings.AppSettings
import com.twobit.driver.settings.SensorType
import com.twobit.driver.settings.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: Repository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    private val _recordStartTime = MutableStateFlow(0L)
    val recordStartTime: StateFlow<Long> = _recordStartTime

    private val _recordEndTime = MutableStateFlow(0L)
    val recordEndTime: StateFlow<Long> = _recordEndTime

    private val _eventType = MutableStateFlow("")
    val eventType: StateFlow<String> = _eventType

    fun toggleRecording(eventType: String) {
        viewModelScope.launch {
            _isRecording.value = !_isRecording.value
            Log.d(TAG, "Recording toggled: ${_isRecording.value}")
            if (_isRecording.value) {
                _recordStartTime.value = System.currentTimeMillis()
            } else {
                _recordEndTime.value = System.currentTimeMillis()
                _eventType.value = eventType
                createEventWithSensorData(
                    _recordStartTime.value,
                    _recordEndTime.value,
                    _eventType.value
                )
            }
        }
    }

    fun createEventWithSensorData(startTime: Long, endTime: Long, eventType: String) {
        viewModelScope.launch {
            val sensorDataList = repository.getSensorDataWithinTimeRange(startTime, endTime)

            // Get the enabled sensors
            val enabledSensors = settingsManager.getEnabledSensors()

            // Filter the sensor data based on the enabled sensors
            val filteredSensorDataList = sensorDataList.filter { sensorData ->
                enabledSensors.containsKey(SensorType.valueOf(sensorData.measurementName))
            }

            // Only create an event if there is at least one sensor data
            if (filteredSensorDataList.isNotEmpty()) {
                val event = Event(
                    startTime = startTime,
                    endTime = endTime,
                    eventType = eventType,
                    sensorDataList = filteredSensorDataList
                )
                repository.insertEvent(event)
                Log.d(TAG, "Event created: $event")
            }
        }
    }

    companion object {
        private const val TAG = "EventViewModel"
    }
}