package com.twobit.driver.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.twobit.driver.settings.SensorType
import com.twobit.driver.settings.UnitType

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Settings Menu",
                modifier = Modifier.padding(8.dp)
            )
            settings?.let {
                UnitTypeSetting(
                    currentUnitType = it.unitType,
                    onUnitTypeSelected = { newUnitType ->
                        viewModel.updateSettings(it.copy(unitType = newUnitType))
                    }
                )

                Card(modifier = Modifier.padding(8.dp)) {
                    Column {
                        Text(
                            text = "Environment Sensors",
                            modifier = Modifier.padding(8.dp)
                        )
                        SensorToggleSettings(
                            currentSensorSettings = it.sensors.filterKeys { sensorType ->
                                sensorType in listOf(SensorType.LIGHT, SensorType.PRESSURE, SensorType.AMBIENT_TEMPERATURE, SensorType.RELATIVE_HUMIDITY)
                            },
                            onSensorToggled = { sensorType, isEnabled ->
                                val updatedSensors = it.sensors.toMutableMap().apply {
                                    this[sensorType] = isEnabled
                                }
                                viewModel.updateSettings(it.copy(sensors = updatedSensors))
                            }
                        )
                    }
                }

                Card(modifier = Modifier.padding(8.dp)) {
                    Column {
                        Text(
                            text = "Motion Sensors",
                            modifier = Modifier.padding(8.dp)
                        )
                        SensorToggleSettings(
                            currentSensorSettings = it.sensors.filterKeys { sensorType ->
                                sensorType in listOf(SensorType.ACCELERATION, SensorType.GRAVITY, SensorType.GYROSCOPE)
                            },
                            onSensorToggled = { sensorType, isEnabled ->
                                val updatedSensors = it.sensors.toMutableMap().apply {
                                    this[sensorType] = isEnabled
                                }
                                viewModel.updateSettings(it.copy(sensors = updatedSensors))
                            }
                        )
                    }
                }

                Card(modifier = Modifier.padding(8.dp)) {
                    Column {
                        Text(
                            text = "Position Sensors",
                            modifier = Modifier.padding(8.dp)
                        )
                        SensorToggleSettings(
                            currentSensorSettings = it.sensors.filterKeys { sensorType ->
                                sensorType in listOf(SensorType.PROXIMITY, SensorType.MAGNETIC_FIELD)
                            },
                            onSensorToggled = { sensorType, isEnabled ->
                                val updatedSensors = it.sensors.toMutableMap().apply {
                                    this[sensorType] = isEnabled
                                }
                                viewModel.updateSettings(it.copy(sensors = updatedSensors))
                            }
                        )
                    }
                }

                Card(modifier = Modifier.padding(8.dp)) {
                    Column {
                        Text(
                            text = "Location Sensors",
                            modifier = Modifier.padding(8.dp)
                        )
                        SensorToggleSettings(
                            currentSensorSettings = it.sensors.filterKeys { sensorType ->
                                sensorType == SensorType.GPS
                            },
                            onSensorToggled = { sensorType, isEnabled ->
                                val updatedSensors = it.sensors.toMutableMap().apply {
                                    this[sensorType] = isEnabled
                                }
                                viewModel.updateSettings(it.copy(sensors = updatedSensors))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SensorToggleSettings(
    currentSensorSettings: Map<SensorType, Boolean>,
    onSensorToggled: (SensorType, Boolean) -> Unit
) {
    Column {
        currentSensorSettings.forEach { (sensorType, isEnabled) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = sensorType.name)
                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isChecked ->
                        onSensorToggled(sensorType, isChecked)
                    }
                )
            }
        }
    }
}

@Composable
fun UnitTypeSetting(currentUnitType: UnitType, onUnitTypeSelected: (UnitType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Unit Type")
        UnitType.values().forEach { unitType ->
            Row(
                Modifier.clickable { onUnitTypeSelected(unitType) }
            ) {
                RadioButton(
                    selected = unitType == currentUnitType,
                    onClick = { onUnitTypeSelected(unitType) }
                )
                Text(text = unitType.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}


