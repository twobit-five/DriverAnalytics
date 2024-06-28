package com.twobit.driver.ui.livedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobit.driver.data.repository.PhoneSensorDataRepository
import com.twobit.driver.data.entities.PhoneSensorData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveDataViewModel @Inject constructor(
    private val phoneSensorDataRepository: PhoneSensorDataRepository
) : ViewModel() {

    private val _phoneSensorData = MutableStateFlow<PhoneSensorData?>(null)
    val phoneSensorData: StateFlow<PhoneSensorData?> get() = _phoneSensorData

    init {
        fetchLatestData()
    }

    private fun fetchLatestData() {
        viewModelScope.launch {
            phoneSensorDataRepository.getLatestDataFlow().collectLatest { data ->
                _phoneSensorData.value = data
            }
        }
    }
}
