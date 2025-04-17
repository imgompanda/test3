package com.example.mydeviceinfo.data.model

data class NetworkInfo(
    val isConnected: Boolean,
    val networkType: String // e.g., "WiFi", "Mobile", "Not Connected"
) 