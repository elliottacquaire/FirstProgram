package com.example.firstprogram.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.orhanobut.logger.Logger


/**
 * ACTION_PACKAGE_ADDED 一个新应用包已经安装在设备上，数据包括包名（最新安装的包程序不能接收到这个广播）
ACTION_PACKAGE_REPLACED 一个新版本的应用安装到设备，替换之前已经存在的版本
ACTION_PACKAGE_CHANGED 一个已存在的应用程序包已经改变，包括包名
ACTION_PACKAGE_REMOVED 一个已存在的应用程序包已经从设备上移除，包括包名（正在被安装的包程序不能接收到这个广播）
ACTION_PACKAGE_RESTARTED 用户重新开始一个包，包的所有进程将被杀死，所有与其联系的运行时间状态应该被移除，包括包名（重新开始包程序不能接收到这个广播）
ACTION_PACKAGE_DATA_CLEARED 用户已经清楚一个包的数据，包括包名（清除包程序不能接收到这个广播）
 */
class AppInstallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val manager = context!!.packageManager
        if (intent?.action.equals(Intent.ACTION_PACKAGE_ADDED)) {
            val packageName = intent!!.data!!.schemeSpecificPart
            Toast.makeText(context, "安装成功$packageName", Toast.LENGTH_LONG).show()
            Logger.d("安装成功$packageName")
        }
        if (intent?.action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            val packageName = intent!!.data!!.schemeSpecificPart
            Toast.makeText(context, "卸载成功$packageName", Toast.LENGTH_LONG).show()
            Logger.d("卸载成功$packageName")
        }
        if (intent?.action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            val packageName = intent!!.data!!.schemeSpecificPart
            Toast.makeText(context, "替换成功$packageName", Toast.LENGTH_LONG).show()
            Logger.d("替换成功$packageName")
        }
    }
}