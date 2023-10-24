package com.twobit.driver.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.twobit.driver.domain.settings.SensorType
import com.twobit.driver.domain.settings.UnitType


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
        ) {
            Text(
                text = "Welcome to Settings Menu",
                modifier = Modifier.padding(8.dp)
            )
            settings?.let {
                UnitTypeSetting(
                    currentUnitType = it.unitType,
                    onUnitTypeSelected = { newUnitType ->
                        viewModel.updateSettings(it.copy(unitType = newUnitType))
                    }
                )
                DriverAgeSetting(
                    currentAge = it.driversAge,
                    onAgeChanged = { newAge ->
                        viewModel.updateSettings(it.copy(driversAge = newAge))
                    }
                )
                SensorToggleSettings(
                    currentSensorSettings = it.sensors,
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
fun DriverAgeSetting(currentAge: Int, onAgeChanged: (Int) -> Unit) {
    var ageText by remember { mutableStateOf(currentAge.toString()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Driver Age")
        BasicTextField(
            value = ageText,
            onValueChange = { value ->
                ageText = value
                onAgeChanged(value.toIntOrNull() ?: currentAge)
            }
        )
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


