package com.example.mydeviceinfo.data.repository

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.example.mydeviceinfo.data.model.StorageInfo
import java.io.File

class StorageRepository(private val context: Context) { // Context is not strictly needed here but kept for consistency

    fun getStorageInfo(): StorageInfo {
        val internalPath: File = Environment.getDataDirectory()
        val statFs = StatFs(internalPath.path)

        val blockSize: Long = statFs.blockSizeLong
        val totalBlocks: Long = statFs.blockCountLong
        val availableBlocks: Long = statFs.availableBlocksLong

        val totalInternalBytes = totalBlocks * blockSize
        val availableInternalBytes = availableBlocks * blockSize

        // Convert bytes to Gigabytes (1 GB = 1024 * 1024 * 1024 bytes)
        val bytesToGb = 1024f * 1024f * 1024f
        val totalInternalGb = totalInternalBytes / bytesToGb
        val availableInternalGb = availableInternalBytes / bytesToGb

        return StorageInfo(
            totalInternalGb = totalInternalGb,
            availableInternalGb = availableInternalGb
        )
    }
} 