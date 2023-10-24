package com.twobit.driver.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()) {

    val isDark = viewModel.isDark
    val lightSensorValue by viewModel.lightSensorValue.observeAsState("N/A")


    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 16.dp)
        ) {
            Text(  // Background text
                text = "Welcome to Driver Analytics",
                //style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.height(16.dp))  // Adds space between the text elements

            Text(  // Light sensor value text
                text = "Light Sensor Value: $lightSensorValue",
                //style = MaterialTheme.typography.body1,
            )
        }

        Button(
            onClick = {
                //TODO Implement
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-20).dp)
        ) {
            Text(text = "Scan for Sensors")
        }
    }
}
