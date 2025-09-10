package ar.villalba.myapplication.getters

import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi

class Screen(localContext: Context) {

    val context = localContext

    @RequiresApi(Build.VERSION_CODES.R)
    val resolution = getScreenResolution()
    @RequiresApi(Build.VERSION_CODES.R)
    val density = getScreenDensity()
    @RequiresApi(Build.VERSION_CODES.R)
    val refreshRate = getScreenRefreshRate()

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
    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenDensity(): String{
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val density = displayMetrics.density
        val densityDpi = displayMetrics.densityDpi


        return """
            $density (dpi: $densityDpi)
            """.trimIndent()
    }
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