package com.patproject.test.servicetestproject.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackgroundService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext,"Background service started",Toast.LENGTH_SHORT).show()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(applicationContext,"Background service destroyed",Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}