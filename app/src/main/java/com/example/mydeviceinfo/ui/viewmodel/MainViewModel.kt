package com.example.mydeviceinfo.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mydeviceinfo.data.model.*
import com.example.mydeviceinfo.data.repository.*

class MainViewModel(
    application: Application,
    internal val batteryRepository: BatteryRepository,
    internal val networkRepository: NetworkRepository,
    internal val deviceRepository: DeviceRepository,
    internal val storageRepository: StorageRepository,
    internal val permissionRepository: PermissionRepository
) : AndroidViewModel(application) {

    private val _batteryInfo = MutableLiveData<BatteryInfo>()
    val batteryInfo: LiveData<BatteryInfo> get() = _batteryInfo

    private val _networkInfo = MutableLiveData<NetworkInfo>()
    val networkInfo: LiveData<NetworkInfo> get() = _networkInfo

    private val _deviceInfo = MutableLiveData<DeviceInfo>()
    val deviceInfo: LiveData<DeviceInfo> get() = _deviceInfo

    private val _storageInfo = MutableLiveData<StorageInfo>()
    val storageInfo: LiveData<StorageInfo> get() = _storageInfo

    private val _locationPermissionInfo = MutableLiveData<LocationPermissionInfo>()
    val locationPermissionInfo: LiveData<LocationPermissionInfo> get() = _locationPermissionInfo

    fun loadBatteryInfo() {
        _batteryInfo.value = batteryRepository.getBatteryInfo()
    }

    fun loadNetworkInfo() {
        _networkInfo.value = networkRepository.getNetworkInfo()
    }

    fun loadDeviceInfo() {
        _deviceInfo.value = deviceRepository.getDeviceInfo()
    }

    fun loadStorageInfo() {
        _storageInfo.value = storageRepository.getStorageInfo()
    }

    fun checkLocationPermission() {
        _locationPermissionInfo.value = permissionRepository.checkLocationPermission()
    }
} 