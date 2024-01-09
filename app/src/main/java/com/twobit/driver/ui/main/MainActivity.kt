package com.twobit.driver.ui.main

import android.Manifest
import android.provider.Settings
import com.twobit.driver.domain.services.SensorService
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twobit.driver.domain.bluetooth.BluetoothBroadcastReceiver
import com.twobit.driver.settings.SettingsManager
import com.twobit.driver.ui.event.EventScreen
import com.twobit.driver.ui.event.EventViewModel
import com.twobit.driver.ui.home.HomeScreen
import com.twobit.driver.ui.home.HomeViewModel
import com.twobit.driver.ui.info.InformationScreen
import com.twobit.driver.ui.permisions.CameraPermissionTextProvider
import com.twobit.driver.ui.permisions.PermissionDialog
import com.twobit.driver.ui.permisions.PermissionViewModel
import com.twobit.driver.ui.permisions.PhoneCallPermissionTextProvider
import com.twobit.driver.ui.permisions.RecordAudioPermissionTextProvider
import com.twobit.driver.ui.settings.SettingsScreen
import com.twobit.driver.ui.settings.SettingsViewModel
import com.twobit.driver.ui.theme.M3NavigationDrawerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String
)


//TODO move these to a separate file?
const val ROUTE_HOME = "home"
const val ROUTE_EVENT = "event"
const val ROUTE_INFORMATION = "information"
const val ROUTE_SETTINGS = "settings"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
    )

    @Inject
    lateinit var bluetoothBroadcastReceiver: BluetoothBroadcastReceiver

    @Inject
    lateinit var settingsManager: SettingsManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceIntent = Intent(this, SensorService::class.java)

        val intentFilter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }
        registerReceiver(bluetoothBroadcastReceiver, intentFilter)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        setContent {
            M3NavigationDrawerTheme {


                //TODO create a permision Manager class to handle this. (Which can be used in other activities)
                val permissionViewModel = viewModel<PermissionViewModel>()
                val dialogQueue = permissionViewModel.visiblePermissionDialogQueue

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) { perms ->
                    permissionsToRequest.forEach { permission ->
                        val isGranted = perms[permission] == true
                        permissionViewModel.onPermissionResult(permission, isGranted)
                    }
                }

                dialogQueue
                    .reversed()
                    .forEach { permission ->
                        PermissionDialog(
                            permissionTextProvider = when (permission) {
                                Manifest.permission.CAMERA -> {
                                    CameraPermissionTextProvider()
                                }
                                Manifest.permission.RECORD_AUDIO -> {
                                    RecordAudioPermissionTextProvider()
                                }
                                Manifest.permission.CALL_PHONE -> {
                                    PhoneCallPermissionTextProvider()
                                }
                                else -> return@forEach
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                permission
                            ),
                            onDismiss = permissionViewModel::dismissDialog,
                            onOkClick = {
                                permissionViewModel.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
                    }
                }

                MainContent()
            }
        }
    }

fun Activity.openAppSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also(::startActivity)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContent() {

    val navController = rememberNavController()

    val homeViewModel = viewModel<HomeViewModel>()
    val eventViewModel = viewModel<EventViewModel>()
    val settingsViewModel = viewModel<SettingsViewModel>()

    val items = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = ROUTE_HOME
        ),
        NavigationItem(
            title = "Event",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = ROUTE_EVENT
        ),
        NavigationItem(
            title = "Information",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            badgeCount = 45,
            route = ROUTE_INFORMATION
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = ROUTE_SETTINGS
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.route)
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Driver Analytics")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                },
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME
                    ) {
                        composable(
                            route = ROUTE_HOME
                        ) {
                            HomeScreen(
                                navController = navController,
                                viewModel = homeViewModel
                            )
                        }
                        composable(
                            route = ROUTE_EVENT
                        ) {
                            EventScreen(
                                navController = navController,
                                viewModel = eventViewModel,
                            )
                        }
                        composable(
                            route = ROUTE_INFORMATION
                        ) {
                            InformationScreen(
                                navController = navController,
                                viewModel = homeViewModel
                            )
                        }
                        composable(
                            route = ROUTE_SETTINGS
                        ) {
                            SettingsScreen(
                                navController = navController,
                                viewModel = settingsViewModel
                            )
                        }
                    }
                }

            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MainContentPreview() {
    MainContent()
}


