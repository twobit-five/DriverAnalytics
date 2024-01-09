package com.twobit.driver.ui.permisions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat

//TODO Does this belong in the UI package?
class PermissionManager(private val activity: Activity) {

    private val permissionsToRequest = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE
    )

    fun requestPermissions() {
        val requestCode = 200 // Define your unique request code here
        ActivityCompat.requestPermissions(
            activity,
            permissionsToRequest,
            requestCode
        )
    }

    fun handlePermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        // Handle permissions granted or denied
    }

    fun shouldShowRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun navigateToAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        activity.startActivity(intent)
    }
}
