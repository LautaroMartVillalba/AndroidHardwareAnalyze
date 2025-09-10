package ar.villalba.myapplication.getters

import android.os.Build

class Device {

    val model: String? = Build.MODEL
    val device: String? = Build.DEVICE
    val brand: String? = Build.BRAND
    val manufacturer: String? = Build.MANUFACTURER
    val androidVersion: String? = Build.VERSION.RELEASE
    val patchVersion: String? = Build.VERSION.SECURITY_PATCH
    val kernelVersion: String? = System.getProperty("os.version")
    val sdkVersion = Build.VERSION.SDK_INT
    val hardware: String? = Build.HARDWARE
    val display: String? = Build.DISPLAY
    val product: String? = Build.PRODUCT
    val architecture: String? = System.getProperty("os.arch")

}