package com.example.firstprogram.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast


class ArouterReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = manager!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isAvailable) {
            Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show()
        }

        MessageManager.messageARouter.onNotification(context, "title", "summary", "extraMap")
    }
}