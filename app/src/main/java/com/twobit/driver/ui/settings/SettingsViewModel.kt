package com.twobit.driver.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobit.driver.settings.AppSettings
import com.twobit.driver.settings.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _settings = MutableStateFlow<AppSettings?>(null)
    val settings: StateFlow<AppSettings?> = _settings

    init {
        viewModelScope.launch {
            _settings.value = settingsManager.getSettings()
        }
    }

    fun updateSettings(newSettings: AppSettings) {
        viewModelScope.launch {
            settingsManager.updateSettings(newSettings)
            _settings.value = newSettings
        }
    }
}