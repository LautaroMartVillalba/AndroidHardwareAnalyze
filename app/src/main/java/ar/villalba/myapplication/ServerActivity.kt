package ar.villalba.myapplication

import android.annotation.SuppressLint
import android.app.ForegroundServiceTypeException
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class ServerActivity: Service() {
    private var server: NanoLocalServer? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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