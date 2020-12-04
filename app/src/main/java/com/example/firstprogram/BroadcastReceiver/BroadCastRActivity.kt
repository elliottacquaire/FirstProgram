package com.example.firstprogram.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firstprogram.R



class BroadCastRActivity : AppCompatActivity() {

    private var receiver : BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broad_cast_r)

        receiver = AppInstallReceiver()

        initReceiver()
    }


    private fun initReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addDataScheme("com.example.firstprogram")
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}