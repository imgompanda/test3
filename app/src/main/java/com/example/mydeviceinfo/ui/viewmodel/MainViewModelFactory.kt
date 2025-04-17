package com.example.mydeviceinfo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydeviceinfo.data.repository.*

class MainViewModelFactory(
    private val application: Application,
    private val batteryRepository: BatteryRepository,
    private val networkRepository: NetworkRepository,
    private val deviceRepository: DeviceRepository,
    private val storageRepository: StorageRepository,
    private val permissionRepository: PermissionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                application,
                batteryRepository,
                networkRepository,
                deviceRepository,
                storageRepository,
                permissionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 