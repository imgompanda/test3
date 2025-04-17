package com.example.mydeviceinfo.data.repository

import android.os.Build
import com.example.mydeviceinfo.data.model.DeviceInfo

class DeviceRepository {

    fun getDeviceInfo(): DeviceInfo {
        return DeviceInfo(
            model = Build.MODEL,
            manufacturer = Build.MANUFACTURER,
            osVersion = Build.VERSION.RELEASE,
            apiLevel = Build.VERSION.SDK_INT
        )
    }
} 