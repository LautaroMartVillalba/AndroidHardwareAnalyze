package ar.villalba.myapplication.getters

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

/**
 * The Sensor class provides access to the device’s built-in hardware sensors.
 *
 * It initializes references to commonly used sensors.
 *
 * Each sensor is retrieved through the system’s `SensorManager`.
 * Some sensors may be unavailable on certain devices, in which case
 * their value will be `null`.
 *
 * @property sensorManager The system service used to manage and access sensors.
 * @property accelerometer The default accelerometer sensor, if available.
 * @property light The default ambient light sensor, if available.
 * @property proximity The default proximity sensor, if available.
 * @property magnetometer The default magnetic field sensor, if available.
 * @property gyroscope The default gyroscope sensor, if available.
 * @property barometer The default pressure sensor, if available.
 */
class Sensor(localContext: Context) {

    var sensorManager: SensorManager = localContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val light: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    val proximity: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    val magnetometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    val barometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

}