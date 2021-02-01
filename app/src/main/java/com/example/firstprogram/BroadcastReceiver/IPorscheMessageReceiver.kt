package com.example.firstprogram.BroadcastReceiver

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface IPorscheMessageReceiver : IProvider {
    fun onNotification(context: Context, title: String?, summary: String?, extraMap: String?)

    fun onNotificationOpened(context: Context?, title: String?, summary: String?, extraMap: String?)

    fun onNotificationRemoved(context: Context?, messageId: String?)

    fun onSysNoticeOpened(context: Context?, title: String?, content: String?, extraMap: Map<String?, String?>)
}