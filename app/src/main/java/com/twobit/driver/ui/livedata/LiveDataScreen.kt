package com.twobit.driver.ui.livedata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun LiveDataScreen(
    viewModel: LiveDataViewModel = hiltViewModel()
) {
    val phoneSensorData by viewModel.phoneSensorData.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp, top = 64.dp) // Adjust the top padding
        .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Phone Sensor Data", style = MaterialTheme.typography.titleMedium)

        phoneSensorData?.let { data ->
            Text(text = "Timestamp: ${data.timestamp}")
            Text(text = "Read Latency: ${data.readLatency} ms")
            Text(text = "Accelerometer X: ${data.accelerometerX}")
            Text(text = "Accelerometer Y: ${data.accelerometerY}")
            Text(text = "Accelerometer Z: ${data.accelerometerZ}")
            Text(text = "Gyroscope X: ${data.gyroscopeX}")
            Text(text = "Gyroscope Y: ${data.gyroscopeY}")
            Text(text = "Gyroscope Z: ${data.gyroscopeZ}")
            Text(text = "Magnetometer X: ${data.magnetometerX}")
            Text(text = "Magnetometer Y: ${data.magnetometerY}")
            Text(text = "Magnetometer Z: ${data.magnetometerZ}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LiveDataScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LiveDataScreen()
    }
}
