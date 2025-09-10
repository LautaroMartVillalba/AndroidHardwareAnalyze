package ar.villalba.myapplication.getters

import android.os.Environment
import android.os.StatFs

/**
 * The Storage class provides information about the device’s internal storage capacity.
 *
 * It exposes the total, available, and used storage space, expressed in megabytes (MB).
 * Data is retrieved using Android’s `StatFs` on the internal data directory.
 *
 * @property total The total storage capacity of the device in MB.
 * @property rest The currently available (free) storage space in MB.
 * @property used The amount of storage currently in use in MB.
 */
class Storage {
    val total = getTotalStorageInfo()
    val rest = getRestStorageInfo()
    val used = total - rest

    /**
     * Retrieves the total storage space of the device’s internal memory.
     *
     * Uses `StatFs` on the internal data directory to calculate
     * the total capacity in megabytes.
     *
     * @return The total storage capacity in MB.
     */
    fun getTotalStorageInfo(): Long{
        val internalPath = Environment.getDataDirectory()
        val stat = StatFs(internalPath.path)

        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong

        val totalSpaceBytes = totalBlocks * blockSize / 1024 / 1024

        return totalSpaceBytes
    }

    /**
     * Retrieves the available storage space of the device’s internal memory.
     *
     * Uses `StatFs` on the internal data directory to calculate
     * the remaining free space in megabytes.
     *
     * @return The available storage space in MB.
     */
    fun getRestStorageInfo(): Long{
        val internalPath = Environment.getDataDirectory()
        val stat = StatFs(internalPath.path)

        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong

        val availableSpaceBytes = availableBlocks * blockSize / 1024 / 1024

        return availableSpaceBytes
    }

}