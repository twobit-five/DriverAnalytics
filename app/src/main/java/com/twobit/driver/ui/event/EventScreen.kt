package com.twobit.driver.ui.event

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import com.google.gson.GsonBuilder
import com.twobit.driver.data.entities.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    navController: NavController,
    viewModel: EventViewModel = viewModel(),
) {
    val isRecording by viewModel.isRecording.collectAsState()
    val recordStartTime by viewModel.recordStartTime.collectAsState()
    val recordEndTime by viewModel.recordEndTime.collectAsState()
    val eventType by viewModel.eventType.collectAsState()
    var eventTypeInput by remember { mutableStateOf("") }

    val gson = GsonBuilder().setPrettyPrinting().create()

    Column {
        TopAppBar(
            title = { Text(text = "Driver Analytics") },
            navigationIcon = {
                IconButton(onClick = { /* Handle navigation icon click */ }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp) // Adjust the height as needed
            ) {
                Canvas(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp)
                        .padding(end = 16.dp) // Adjust the end padding as needed
                ) {
                    drawCircle(color = if (isRecording) Color.Red else Color.Green)
                }
            }

            TextField(
                value = eventTypeInput,
                onValueChange = { eventTypeInput = it },
                placeholder = { Text(text = "Event Type") },
                modifier = Modifier.padding(16.dp)
            )

            if (!isRecording && recordStartTime != 0L && recordEndTime != 0L) {
                Text(text = "Record Start Time: $recordStartTime", modifier = Modifier.padding(start = 16.dp, end = 16.dp))
                Text(text = "Record End Time: $recordEndTime", modifier = Modifier.padding(start = 16.dp, end = 16.dp))

                val event = Event(
                    startTime = recordStartTime,
                    endTime = recordEndTime,
                    eventType = eventType,
                    sensorDataList = listOf() // Replace with actual sensor data list
                )

                val eventJson = gson.toJson(event)

                Card(modifier = Modifier.padding(16.dp)) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        item {
                            Text(text = eventJson)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.toggleRecording(eventTypeInput)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                Text(text = if (isRecording) "Stop Record" else "Record Event")
            }
        }
    }
}