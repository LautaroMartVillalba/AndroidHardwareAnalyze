package ar.villalba.myapplication.getters

import android.os.Build
import androidx.annotation.RequiresApi
import org.json.JSONObject

/**
 * The JSONResponse class is responsible for building a structured JSON
 * representation of multiple device information modules (battery, CPU,
 * device, screen, RAM, sensors, and storage).
 *
 * Each hardware/software component is serialized into a JSONObject
 * and appended to the final response object.
 */
class JSONResponse {

    /**
     * Creates a unified JSON response containing detailed information
     * about the device, aggregating different hardware and software metrics.
     *
     * @param battery The [Battery] instance with battery status and properties.
     * @param cpu The [CPU] instance with CPU core and frequency information.
     * @param device The [Device] instance with device and system properties.
     * @param screen The [Screen] instance with display metrics.
     * @param ram The [RAM] instance with total and available memory.
     * @param sensor The [Sensor] instance with sensor availability details.
     * @param storage The [Storage] instance with storage space metrics.
     *
     * @return A [JSONObject] containing all the collected information
     *         in a hierarchical structure.
     *
     * Example output:
     * ```
     * {
     *   "battery": { "percent": 85, "status": "Charging", ... },
     *   "cpu": { "cores": 8, "minFreq": [...], "maxFreq": [...], ... },
     *   "device": { "model": "Pixel 7", "androidVersion": "13", ... },
     *   "screen": { "resolution": "1080x2400", "density": 420, ... },
     *   "ram": { "total": 6144, "available": 3200 },
     *   "sensor": { "accelerometer": true, "gyroscope": true, ... },
     *   "storage": { "total": 128000, "rest": 45000, "used": 83000 }
     * }
     * ```
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun createJSONResponse(battery: Battery?, cpu: CPU?, device: Device?, screen: Screen?, ram: RAM?, sensor: Sensor?, storage: Storage?): JSONObject {
        val generalResponse = JSONObject()
        val batteryJson = JSONObject()
        batteryJson.put("percent", battery?.batteryStatus)
        batteryJson.put("status", battery?.batteryStatus)
        batteryJson.put("charging", battery?.batteryCharging)
        batteryJson.put("temperature", battery?.batteryTempAndVolt)
        generalResponse.put("battery", batteryJson)

        val CPUJson = JSONObject()
        CPUJson.put("cores", cpu?.numberOfCores)
        CPUJson.put("currentFreq", cpu?.currentCoreFreq)
        CPUJson.put("minFreq", cpu?.minCoreFreq)
        CPUJson.put("maxFreq", cpu?.maxCoreFreq)
        CPUJson.put("name", cpu?.cpuName)
        generalResponse.put("cpu", CPUJson)

        val deviceJson = JSONObject()
        deviceJson.put("model", device?.model)
        deviceJson.put("device", device?.device)
        deviceJson.put("brand", device?.brand)
        deviceJson.put("manufacturer", device?.manufacturer)
        deviceJson.put("androidVersion", device?.androidVersion)
        deviceJson.put("patchVersion", device?.patchVersion)
        deviceJson.put("kernelVersion", device?.kernelVersion)
        deviceJson.put("sdkVersion", device?.sdkVersion)
        deviceJson.put("hardware", device?.hardware)
        deviceJson.put("display", device?.display)
        deviceJson.put("product", device?.product)
        deviceJson.put("architecture", device?.architecture)
        generalResponse.put("device", deviceJson)

        val screenJson = JSONObject()
        screenJson.put("resolution", screen?.resolution)
        screenJson.put("density", screen?.density)
        screenJson.put("refreshRate", screen?.refreshRate)
        generalResponse.put("screen", screenJson)

        val RAMJson = JSONObject()
        RAMJson.put("total", ram?.totalRam)
        RAMJson.put("available", ram?.availableRam)
        generalResponse.put("ram", RAMJson)

        val sensorJson = JSONObject()
        sensorJson.put("accelerometer", sensor?.accelerometer)
        sensorJson.put("light", sensor?.light)
        sensorJson.put("proximity", sensor?.proximity)
        sensorJson.put("magnetometer", sensor?.magnetometer)
        sensorJson.put("gyroscope", sensor?.gyroscope)
        sensorJson.put("barometer", sensor?.barometer)
        generalResponse.put("sensor", sensorJson)

        val storageJson = JSONObject()
        storageJson.put("total", storage?.total)
        storageJson.put("rest", storage?.rest)
        storageJson.put("used", storage?.used)
        generalResponse.put("storage", storageJson)

        return generalResponse
    }

}