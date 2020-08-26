package com.example.firstprogram.service

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {

    /**
     *  Intent intentService = new Intent(this,MyIntentService.class);
        startService(intentService);
     */


    override fun onHandleIntent(p0: Intent?) {
        for( i in 0..5) {
           //打印当前线程的id
             Log.d("MyIntentService","IntentService线程的id是："+Thread.currentThread().id);
             try {
                 Thread.sleep(1000);
             } catch (e :InterruptedException) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
         }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyIntentService", "onCreate");
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyIntentService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestroy");
    }
}