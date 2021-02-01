package com.example.firstprogram.BroadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.firstprogram.FirstActivity
import com.example.firstprogram.R
import com.orhanobut.logger.Logger

@Route(path = "/main/push")
class NoticifyMessage : IPorscheMessageReceiver {
    override fun onNotification(
        context: Context,
        title: String?,
        summary: String?,
        extraMap: String?
    ) {
        Logger.d("onNotification-------$title------")
        showNotification(context,message = "ddd",_title = "tttt")
    }

    override fun onNotificationOpened(
        context: Context?,
        title: String?,
        summary: String?,
        extraMap: String?
    ) {

    }

    override fun onNotificationRemoved(context: Context?, messageId: String?) {

    }

    override fun onSysNoticeOpened(
        context: Context?,
        title: String?,
        content: String?,
        extraMap: Map<String?, String?>
    ) {

    }

    override fun init(context: Context?) {

    }


    private fun showNotification(
        context: Context,
        message: String,
        _title: String
    ) {

        val intent = Intent(context, FirstActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("textTitle")
            .setContentText("textContent")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }


    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val descriptionText = context.getString(R.string.dk_view_check_info_id)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}