package ar.villalba.myapplication.getters

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

class Sensor(localContext: Context) {

    var sensorManager: SensorManager = localContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val light: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    val proximity: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    val magnetometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    val barometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

}