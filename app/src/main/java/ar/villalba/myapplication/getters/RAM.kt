package ar.villalba.myapplication.getters

import android.app.ActivityManager
import android.content.Context

class RAM (localContext: Context){
    var activityManager = localContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    var totalRam = obtainTotalRam()
    var availableRam = obtainAvailableRam()

    fun obtainTotalRam(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem / 1024 / 1024
    }

    fun obtainAvailableRam(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem / 1024 / 1024
    }


}