package com.twobit.driver.settings

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appSettingsSerializer: AppSettingsSerializer
) {

    private val settingsDataStore: DataStore<AppSettings> = DataStoreFactory.create(
        serializer = appSettingsSerializer,
        produceFile = { context.dataStoreFile("settings.json") }
    )

    val settingsFlow: Flow<AppSettings> = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading settings: ", exception)
                emit(AppSettingsSerializer.defaultValue)
            } else {
                throw exception
            }
        }

    suspend fun getSettings(): AppSettings {
        val settings = settingsDataStore.data.first()
        Log.d(TAG, "Retrieved settings: $settings")
        return settings
    }

    suspend fun updateSettings(newSettings: AppSettings) {
        settingsDataStore.updateData { currentSettings ->
            Log.d(TAG, "Updating settings from: $currentSettings\n to: $newSettings")
            newSettings
        }
    }

    suspend fun getEnabledSensors(): Map<SensorType, Boolean> {
        val settings = getSettings()
        val enabledSensors = settings.sensors.filterValues { it }
        Log.d(TAG, "Retrieved enabled sensors: $enabledSensors")
        return enabledSensors
    }

    companion object {
        private const val TAG = "SettingsManager"
    }
}