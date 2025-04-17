package com.example.mydeviceinfo.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.mydeviceinfo.data.model.LocationPermissionInfo

class PermissionRepository(private val context: Context) {

    fun checkLocationPermission(): LocationPermissionInfo {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // For simplicity, we only check for FINE location here.
        // Coarse location could also be checked if needed.
        return LocationPermissionInfo(isGranted = fineLocationGranted)
    }
} 