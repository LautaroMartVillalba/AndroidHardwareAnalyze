package ar.villalba.myapplication.getters

import android.app.ActivityManager
import android.content.Context

/**
 * The RAM class provides information about the device’s memory usage.
 *
 * It exposes both the total amount of RAM available on the device
 * and the currently available (free) RAM. The class relies on
 * Android’s `ActivityManager` service to gather this data.
 *
 * All values are expressed in megabytes (MB).
 *
 * @property activityManager The system service used to access memory information.
 * @property totalRam The total amount of RAM on the device, in MB.
 * @property availableRam The currently available RAM on the device, in MB.
 */
class RAM (localContext: Context){
    var activityManager = localContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    var totalRam = obtainTotalRam()
    var availableRam = obtainAvailableRam()

    /**
     * Retrieves the total physical memory of the device.
     *
     * Uses `ActivityManager.MemoryInfo.totalMem` to determine the
     * complete RAM capacity of the system.
     *
     * @return The total RAM in MB.
     */
    fun obtainTotalRam(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem / 1024 / 1024
    }

    /**
     * Retrieves the currently available memory of the device.
     *
     * Uses `ActivityManager.MemoryInfo.availMem` to determine the
     * amount of free RAM that can be allocated to applications.
     *
     * @return The available RAM in MB.
     */
    fun obtainAvailableRam(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem / 1024 / 1024
    }


}