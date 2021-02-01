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

        //注册“网络变化”的广播接收器
        //注册“网络变化”的广播接收器
        val intentFilters = IntentFilter()
        intentFilters.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val networkChangeReceiver = ArouterReceiver()
        registerReceiver(networkChangeReceiver, intentFilters)

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