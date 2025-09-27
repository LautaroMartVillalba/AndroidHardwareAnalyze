package ar.villalba.myapplication.getters

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * The JSONResponse class is responsible for building a structured JSON
 * representation of multiple device information modules (battery, CPU,
 * device, screen, RAM, sensors, and storage).
 *
 * Each hardware/software component is serialized into a JSONObject
 * and appended to the final response object.
 */
class JSONManager(localContext: Context) {
    val context = localContext

    @RequiresApi(Build.VERSION_CODES.P)
    fun batteryJson(): JSONObject {
        val battery = Battery(context)
        val batteryJson = JSONObject()
        batteryJson.put("status", battery.batteryStatus)
        batteryJson.put("charging", battery.batteryCharging)
        batteryJson.put("temperature", battery.batteryTempAndVolt)
        return batteryJson
    }

    fun CPUJson(): JSONObject {
        val cpu = CPU()
        val CPUJson = JSONObject()
        CPUJson.put("cores", cpu.numberOfCores)
        CPUJson.put("currentFreq", cpu.currentCoreFreq)
        CPUJson.put("minFreq", cpu.minCoreFreq)
        CPUJson.put("maxFreq", cpu.maxCoreFreq)
        CPUJson.put("name", cpu.cpuName)
        return CPUJson
    }

    fun deviceJson(): JSONObject {
        val device = Device()
        val deviceJson = JSONObject()
        deviceJson.put("model", device.model)
        deviceJson.put("device", device.device)
        deviceJson.put("brand", device.brand)
        deviceJson.put("manufacturer", device.manufacturer)
        deviceJson.put("androidVersion", device.androidVersion)
        deviceJson.put("patchVersion", device.patchVersion)
        deviceJson.put("kernelVersion", device.kernelVersion)
        deviceJson.put("sdkVersion", device.sdkVersion)
        deviceJson.put("hardware", device.hardware)
        deviceJson.put("display", device.display)
        deviceJson.put("product", device.product)
        deviceJson.put("architecture", device.architecture)
        return deviceJson
    }

    fun ramJson(): JSONObject {
        val ram = RAM(context)
        val RAMJson = JSONObject()
        RAMJson.put("total", ram.totalRam)
        RAMJson.put("available", ram.availableRam)
        return RAMJson
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun screenJson(): JSONObject {
        val screen = Screen(context)
        val screenJson = JSONObject()
        screenJson.put("resolution", screen.resolution)
        screenJson.put("density", screen.density)
        screenJson.put("refreshRate", screen.refreshRate)
        return screenJson
    }

    fun storageJson(): JSONObject {
        val storage = Storage()
        val storageJson = JSONObject()
        storageJson.put("total", storage.total)
        storageJson.put("rest", storage.rest)
        storageJson.put("used", storage.used)
        return storageJson
    }

}