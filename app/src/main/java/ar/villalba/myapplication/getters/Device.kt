package ar.villalba.myapplication.getters

import android.os.Build

/**
 * The Device class provides a structured collection of
 * hardware and software information about the current device.
 *
 * This class exposes key attributes related to the device’s
 * model, manufacturer, OS version, kernel version, hardware
 * specifications, and system architecture. All fields are
 * retrieved directly from Android’s `Build` constants or
 * system properties.
 *
 * @property model The end-user visible name for the device (e.g., "Pixel 6").
 * @property device The industrial design name or internal device codename.
 * @property brand The brand associated with the device (e.g., "Google", "Samsung").
 * @property manufacturer The original manufacturer of the device hardware.
 * @property androidVersion The release version string of the Android OS (e.g., "13").
 * @property patchVersion The security patch level of the OS.
 * @property kernelVersion The underlying Linux kernel version.
 * @property sdkVersion The Android API level as an integer (e.g., 33).
 * @property hardware The hardware name, usually identifying the SoC or board.
 * @property display The build ID string displayed to users.
 * @property product The overall product name assigned to the device.
 * @property architecture The system architecture (e.g., "arm64-v8a").
 */
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