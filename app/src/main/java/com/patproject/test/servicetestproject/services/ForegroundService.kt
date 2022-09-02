package com.patproject.test.servicetestproject.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.patproject.test.servicetestproject.R

class ForegroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext,"Background service started", Toast.LENGTH_SHORT).show()
        val channel = NotificationChannel(channelID,channelID,NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = Notification.Builder(applicationContext,channelID)
            .setContentText("SERVICE IS RUNNING")
            .setContentTitle("Service is enabled")
            .setSmallIcon(R.drawable.ic_launcher_background)

        startForeground(notificationID,notification.build())
        return super.onStartCommand(intent, flags, startId)
    }
    companion object{
        const val channelID = "foreground service id"
        const val notificationID = 1111
    }
}