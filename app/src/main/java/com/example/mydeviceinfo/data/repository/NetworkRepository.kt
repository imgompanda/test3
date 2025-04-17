package com.example.mydeviceinfo.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.mydeviceinfo.data.model.NetworkInfo

class NetworkRepository(private val context: Context) {

    @Suppress("DEPRECATION") // Needed for older API levels
    fun getNetworkInfo(): NetworkInfo {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return NetworkInfo(false, "Not Connected")
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkInfo(false, "Not Connected")

            return NetworkInfo(
                isConnected = true,
                networkType = when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Mobile"
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
                    else -> "Other"
                }
            )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return NetworkInfo(
                    isConnected = true,
                    networkType = when (activeNetworkInfo.type) {
                        ConnectivityManager.TYPE_WIFI -> "WiFi"
                        ConnectivityManager.TYPE_MOBILE -> "Mobile"
                        ConnectivityManager.TYPE_ETHERNET -> "Ethernet"
                        else -> "Other"
                    }
                )
            } else {
                return NetworkInfo(false, "Not Connected")
            }
        }
    }
} 