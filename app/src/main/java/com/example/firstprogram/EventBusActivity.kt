package com.example.firstprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.firstprogram.eventbus.CustomEven
import com.example.firstprogram.eventbus.CustomEventBus
import com.example.firstprogram.eventbus.Subscrobe
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_event_bus.*
import java.lang.Thread.sleep

class EventBusActivity : AppCompatActivity() ,View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus)
        CustomEventBus.register(this)

        customEventBusss.setOnClickListener(this)

        val thread = Thread(Runnable {
            sleep(5)
            Logger.d("-线程开启--${Thread.currentThread()}----")
            CustomEventBus.postEvent(true)
        }).start()

    }

    override fun onDestroy() {
        super.onDestroy()
        CustomEventBus.unRegister(this)
    }

    @Subscrobe(threadModel = "main", isThicky = false)
    fun sendEventBusMessage(event: CustomEven) {
        Logger.d("---event bus message handle ${event.name}--")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.customEventBusss -> {

//                CustomEventBus.postEvent(true)

                val intent = Intent(this, AnnotationActivity().javaClass)
                startActivity(intent)
            }
        }
    }
}