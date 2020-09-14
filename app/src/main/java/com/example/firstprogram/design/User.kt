package com.example.firstprogram.design

import android.util.Log

class User : IUserDao {
    private val TAG = "aaa"
    override fun save(): Int {
        Log.i(TAG,"----save---")
        return 10
    }

    override fun delete(name: String) {
        Log.i(TAG,"-delete---$name")
    }
}