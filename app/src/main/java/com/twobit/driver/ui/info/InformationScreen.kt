package com.twobit.driver.ui.info

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.twobit.driver.ui.home.HomeViewModel

@Composable
fun InformationScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()) {

    Text(text = "Information Screen")
}