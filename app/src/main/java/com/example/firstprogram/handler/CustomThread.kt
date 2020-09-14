package com.example.firstprogram.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class CustomThread : Thread {
    private val TAG = "aaa"

    private var handler : Handler? = null

    constructor(name: String) : super(name)

    override fun run() {
        super.run()
        Log.i(TAG,"--${this.name}----run")
//        Looper.prepare()
        Looper.prepare();// Looper 初始化, 使用Handler Looper消息机制必须要初始化Looper
//        handler = Handler(Looper.myLooper());// 获取当前调用线程中ThreadLocal缓存的Looper对象
        Looper.loop();//Handler机制的大前提, 使调用线程进入死循环, 没错, Android中主线程一直都在死循环

//        mHandler.getLooper().quit();//终止 Looper.looper() 死循环, 执行 quit后Handler机制将失效, 执行时如
        //果MessageQueue中还有Message未执行, 将不会执行未执行Message, 直接退出, 调用quit后将不能发消息给Handler
    }

    override fun start() {
        super.start()
        Log.i(TAG,"--handleMessage----start")
    }

    fun getHandler() : Handler? {
        handler = object: Handler(Looper.myLooper()) {
            override fun handleMessage(msg : Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    //更新ui
                    Log.i(TAG,"--getHandler----${msg.arg1}")
                }
            }
        }
//        handler = Handler(Looper.myLooper())
        Log.i(TAG,"---$handler---")
        return handler
    }
}