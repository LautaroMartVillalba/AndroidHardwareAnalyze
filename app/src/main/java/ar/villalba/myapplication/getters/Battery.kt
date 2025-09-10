package ar.villalba.myapplication.getters

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi

class Battery(localContext: Context) {

    val context = localContext

    @RequiresApi(Build.VERSION_CODES.P)
    val batteryStatus = obtainBatteryStatus()
    @RequiresApi(Build.VERSION_CODES.P)
    val batteryCharging = obtainBatteryCharging()
    @RequiresApi(Build.VERSION_CODES.P)
    val batteryTempAndVolt = obtainBatteryTempAndVolt()

    @RequiresApi(Build.VERSION_CODES.P)
    fun obtainBatteryStatus(): String {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val percent = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val statusString = when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
            BatteryManager.BATTERY_STATUS_FULL -> "Full"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Connected without charging"
            BatteryManager.BATTERY_STATUS_UNKNOWN -> "Disconnected"
            else -> "N/A"
        }

        return """
            Percent: $percent%
            Status: $statusString
            """.trimIndent()
    }
    @RequiresApi(Build.VERSION_CODES.P)
    fun obtainBatteryCharging(): String {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        val plugged = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val pluggedString = when (plugged) {
            BatteryManager.BATTERY_PLUGGED_AC -> "AC"
            BatteryManager.BATTERY_PLUGGED_USB -> "USB"
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
            else -> "N/A"
        }

        return """
            Charging by: $pluggedString
            """.trimIndent()
    }
    @RequiresApi(Build.VERSION_CODES.P)
    fun obtainBatteryTempAndVolt(): String {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        val temperature = (intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1) / 10.0

        return """
               Temp (°C): $temperature
               """.trimIndent()
    }

}