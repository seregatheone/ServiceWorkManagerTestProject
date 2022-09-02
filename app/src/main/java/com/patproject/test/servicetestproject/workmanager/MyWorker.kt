package com.patproject.test.servicetestproject.workmanager

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MyWorker(private val context: Context, workerParams : WorkerParameters) : CoroutineWorker(context,workerParams) {
    override suspend fun doWork(): Result {
        return when(getFive()){
            5-> Result.success()
            else -> Result.failure()
        }
    }
    private suspend fun getFive() : Int{
        val res = flow {
            for (i in 0..5){
                emit(i)
                delay(1000)
            }
        }
        res.collect{
            Toast.makeText(context,"Current number is $it",Toast.LENGTH_SHORT).show()
        }
        return 5
    }
}