package com.twobit.driver.ui.main

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Information : Screen("information")
    object Settings : Screen("settings")
    // ... other screens
}
