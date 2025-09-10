package ar.villalba.myapplication.getters

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * The Battery class provides detailed information about the device's battery state.
 *
 * It retrieves metrics such as the current charge percentage, charging status,
 * charging source, and temperature. This class is designed to encapsulate all
 * battery-related queries, ensuring a single point of access for battery data.
 *
 * Requirements:
 * - API level 28 (Android P) or higher for most features.
 *
 * @property context The application context used to access system services and broadcast intents.
 * @property batteryStatus Pre-fetched string describing the battery percentage and general status.
 * @property batteryCharging Pre-fetched string describing the current charging method (AC, USB, Wireless).
 * @property batteryTempAndVolt Pre-fetched string describing the current battery temperature in Celsius.
 */
class Battery(localContext: Context) {

    val context = localContext

    @RequiresApi(Build.VERSION_CODES.P)
    val batteryStatus = obtainBatteryStatus()
    @RequiresApi(Build.VERSION_CODES.P)
    val batteryCharging = obtainBatteryCharging()
    @RequiresApi(Build.VERSION_CODES.P)
    val batteryTempAndVolt = obtainBatteryTempAndVolt()

    /**
     * Retrieves the current battery percentage and general status.
     *
     * The status may include states such as Charging, Discharging,
     * Full, Connected without charging, or Disconnected.
     *
     * @return A formatted string with the current battery percentage and status.
     */
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

    /**
     * Identifies the power source currently used for charging the battery.
     *
     * The result indicates whether the device is charging via AC, USB,
     * Wireless charging, or not charging at all.
     *
     * @return A formatted string with the charging method.
     */
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

    /**
     * Retrieves the current battery temperature.
     *
     * The temperature is expressed in degrees Celsius and is obtained from
     * the battery change broadcast intent.
     *
     * @return A formatted string with the battery temperature in Celsius.
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun obtainBatteryTempAndVolt(): String {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        val temperature = (intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: -1) / 10.0

        return """
               Temp (°C): $temperature
               """.trimIndent()
    }

}