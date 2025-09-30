package ar.villalba.myapplication.getters

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import org.json.JSONObject
import kotlin.let

/**
 * The Sensor class provides access to the device’s built-in hardware sensors.
 *
 * It initializes references to commonly used sensors.
 *
 * Each sensor is retrieved through the system’s `SensorManager`.
 * Some sensors may be unavailable on certain devices, in which case
 * their value will be `null`.
 *

 */
class SensorInDevice(localContext: Context): SensorEventListener {

    var sensorManager: SensorManager = localContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensorData: MutableMap<String, JSONObject> = mutableMapOf()
    var onUpdateCallback: (() -> Unit)? = null

    fun start(){
        listOf(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_PROXIMITY,
            Sensor.TYPE_PRESSURE
        ).forEach { type ->
            sensorManager.getDefaultSensor(type)?.also {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return Unit
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val valuesJson = JSONObject()
            it.values.forEachIndexed { index, value -> valuesJson.put("v$index", value)}
            sensorData[it.sensor.name] = valuesJson

            onUpdateCallback?.invoke()
        }
    }

    fun toJson(): JSONObject {
        val json = JSONObject()
        for ((name, values) in sensorData) {
            json.put(name, values)
        }
        return json
    }

}