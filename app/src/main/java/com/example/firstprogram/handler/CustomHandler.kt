package com.example.firstprogram.handler

import android.os.Handler
import android.os.Message
import android.util.Log

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