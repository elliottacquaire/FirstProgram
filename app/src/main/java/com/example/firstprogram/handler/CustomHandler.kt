package com.example.firstprogram.handler

import android.os.Handler
import android.os.Message
import android.util.Log

/**
 * 1个线程（Thread）只能绑定 1个循环器（Looper），但可以有多个处理者（Handler）
1个循环器（Looper） 可绑定多个处理者（Handler）
1个处理者（Handler） 只能绑定1个1个循环器（Looper）
 */
class CustomHandler : Handler() {
    private val TAG = "aaa"
    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when(msg.what){
            0 -> {
                Log.i(TAG,"--handleMessage----${msg.arg1}")
            }
        }
    }
}