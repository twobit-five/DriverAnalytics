package com.twobit.driver.ui.event

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.twobit.driver.settings.SettingsManager
import com.twobit.driver.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    navController: NavController,
    viewModel: EventViewModel = viewModel(),
) {
    val isRecording by viewModel.isRecording.collectAsState()
    val recordStartTime by viewModel.recordStartTime.collectAsState()
    val recordEndTime by viewModel.recordEndTime.collectAsState()
    val settings by viewModel.settingsFlow.collectAsState(null)
    var eventType by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            TextField(
                value = if (isRecording) "Recording..." else "Not Recording",
                onValueChange = {},
                placeholder = { Text(text = "Recording Status") },
                enabled = false
            )
            if (!isRecording && recordStartTime != 0L && recordEndTime != 0L) {
                Text(text = "Record Start Time: $recordStartTime")
                Text(text = "Record End Time: $recordEndTime")
            }
            // Display settings
            settings?.let {
                Text(text = "Unit Type: ${it.unitType}")
                it.sensors.forEach { (sensorType, isEnabled) ->
                    Text(text = "$sensorType: ${if (isEnabled) "Enabled" else "Disabled"}")
                }
            }
            TextField(
                value = eventType,
                onValueChange = { eventType = it },
                placeholder = { Text(text = "Event Type") }
            )
        }

        Button(
            onClick = {
                viewModel.toggleRecording(eventType)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = if (isRecording) "Stop Record" else "Record Event")
        }
    }
}