package com.example.firstprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.EventLog
import com.example.firstprogram.event.EventMessage
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBugActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bug)

        EventBus.getDefault().register(this)

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().post(EventMessage(10,"event bus message post"))
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveMsg(message : EventMessage) {
        Logger.e("onReceiveMsg: $message")
    }

}