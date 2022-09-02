package com.patproject.test.servicetestproject.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoundService : Service(){
    private val binder = LocalBinder()
    override fun onBind(p0: Intent?): IBinder {
        return binder
    }
    inner class LocalBinder : Binder(){
        fun getService() : BoundService{
            return this@BoundService
        }
    }


    override fun onUnbind(intent: Intent?): Boolean {
        return false
    }

    fun onButtonPressed(){
        Toast.makeText(applicationContext,"Bound Service watching you",Toast.LENGTH_SHORT).show()
    }
}