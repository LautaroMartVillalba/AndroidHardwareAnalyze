package ar.villalba.myapplication.getters

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi

/**
 * The Screen class provides detailed information about the device’s display.
 *
 * It retrieves and exposes attributes such as resolution, pixel density,
 * and refresh rate. This class centralizes all screen-related data queries,
 * allowing for easy access to display characteristics.
 *
 * Requirements:
 * - API level 30 (Android R) or higher for most features.
 *
 * @property context The application context used to access display services.
 * @property resolution The screen resolution in pixels (e.g., "1080x2400").
 * @property density The screen density as a scale factor with its DPI value.
 * @property refreshRate The current refresh rate of the display in Hz.
 */
class Screen(localContext: Context) {

    val context = localContext

    @RequiresApi(Build.VERSION_CODES.R)
    val resolution = getScreenResolution()
    @RequiresApi(Build.VERSION_CODES.R)
    val density = getScreenDensity()
    @RequiresApi(Build.VERSION_CODES.R)
    val refreshRate = getScreenRefreshRate()

    /**
     * Retrieves the physical resolution of the device’s display in pixels.
     *
     * Uses `WindowManager.currentWindowMetrics` to access the display bounds.
     *
     * @return A formatted string containing the resolution in "width x height" format.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenResolution(): String{
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = windowManager.currentWindowMetrics

        val bounds = metrics.bounds
        val width = bounds.width()
        val height = bounds.height()

        return """
            ${width}x${height}
            """.trimIndent()
    }

    /**
     * Retrieves the screen density information.
     *
     * Combines the logical density scale and the density DPI value
     * for precise representation of display density.
     *
     * @return A formatted string containing the density scale and its DPI value.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenDensity(): String{
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val density = displayMetrics.density
        val densityDpi = displayMetrics.densityDpi


        return """
            $density (dpi: $densityDpi)
            """.trimIndent()
    }

    /**
     * Retrieves the refresh rate of the device’s display.
     *
     * Uses `DisplayManager` to access the current display mode
     * and extract its refresh rate.
     *
     * @return A formatted string containing the refresh rate in Hz.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenRefreshRate(): String{
        val displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val display = displayManager.getDisplay(android.view.Display.DEFAULT_DISPLAY)
        val refreshRate = display?.mode?.refreshRate ?: 0f

        return """
            $refreshRate Hz
            """.trimIndent()
    }

}