package ar.villalba.myapplication.server

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ar.villalba.myapplication.R

class ServerService: Service() {
    private var server: NanoLocalServer? = null

    /**
     * Called when the app is started, either by a call to `startService()`
     * or as a result of the system restarting the service after it was killed.
     *
     * This method is responsible for starting the NanoHTTPD local server and creating
     * a persistent notification to keep the service running in the foreground.
     *
     * The notification informs the user that the server is active and provides
     * a visual indication that the application is performing background work.
     * For devices running Android Oreo (API level 26) and later, a specific
     * notification channel is created.
     *
     * In case of any exception during the start of the foreground service
     * (e.g., due to foreground service start restrictions in recent Android versions
     * if certain requirements are not met), an error will be logged.
     *
     * @param intent The Intent supplied to `startService()`, if any. This may be null.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific start request.
     * @return The return value indicates how the system should behave if the service
     *         is killed. In this case, `START_STICKY` is returned, which means
     *         that if the service process is killed, the system will try to
     *         restart the service, but the original Intent will not be redelivered.
     *         `onStartCommand` will be called with a null Intent in this case,
     *         unless there are pending start Intents.
     */
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        server = NanoLocalServer(5200, this)
        server?.startSv()

        val notification = NotificationCompat.Builder(this, "ServerChannel")
            .setContentTitle("Hardware Analyzer.")
            .setContentText("App is running.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ServerChannel",
                "LocalServer",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        try {
            startForeground(1, notification)
        }catch (e: Exception){
            Log.e("LocalServer", "An error has occurred.", e)
        }

        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onDestroy() {
        server?.stopSv()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}