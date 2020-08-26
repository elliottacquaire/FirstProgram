package com.example.firstprogram.service

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.JobIntentService

class YourIntentService : JobIntentService() {



    companion object{
        private val JOB_ID = 1111

        @JvmStatic
        fun enqueueWork(context: Context?, work: Intent?) {
            enqueueWork(context!!, YourIntentService::class.java, JOB_ID, work!!)
        }

    }


    override fun onCreate() {
        super.onCreate()
        Log.d("YourIntentService","onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("YourIntentService","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("YourIntentService","onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("YourIntentService","onBind")
        return super.onBind(intent)
    }

    override fun isStopped(): Boolean {
        Log.d("YourIntentService","isStopped")
        return super.isStopped()
    }

    override fun onStopCurrentWork(): Boolean {
        Log.d("YourIntentService","onStopCurrentWork")
        return super.onStopCurrentWork()
    }


    override fun onHandleWork(intent: Intent) {
        var ss =  intent.getStringExtra("work")?:"aaa"
        Log.d("YourIntentService","onHandleWork--$ss")
        for (i in 0..10){
            Log.d("YourIntentService","onHandleWork----$i")
        }

    }
}