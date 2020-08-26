package com.example.firstprogram.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


class MyService : Service() {

    private val TAG = "service"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate");
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    /**
     * START_NOT_STICKY:“非粘性的”。使用这个返回值时，如果在执行完onStartCommand方法后，服务被异常kill掉，系统不会自动重启该服务。
    START_STICKY：如果Service进程被kill掉，保留Service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建Service，
    由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到Service，那么参数Intent将为null。
    START_REDELIVER_INTENT：重传Intent。使用这个返回值时，系统会自动重启该服务，并将Intent的值传入
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy");
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent)
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(TAG, "onBind")
        return mBinder
    }

    private val mBinder = MyBinder()

    class MyBinder : Binder(){
        override fun isBinderAlive(): Boolean {
            return super.isBinderAlive()
        }
        fun startDown(){
            Log.d("MyBinder", "startDown")
        }
    }
}