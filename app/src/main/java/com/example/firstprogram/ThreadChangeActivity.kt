package com.example.firstprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_thread_change.*

class ThreadChangeActivity : AppCompatActivity() , View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_change)

        Logger.d("current thread-----${Thread.currentThread().name}")
        main.setOnClickListener(this)
        thread.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main -> {
                val ta = Thread(MyTask())
                ta.start()
            }
            R.id.thread -> {
                val task = Task("customThread")
                task.start()
            }
        }
    }


}

internal class Task(name : String) : Thread(name){

    override fun run() {
        super.run()
        Looper.prepare()
        val looper = Looper.myLooper()
        Logger.d("custom thread run 111--$looper-- ${currentThread().name}")
        try {
            sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Looper.loop()
        Logger.d("custom thread run 222---- ${currentThread().name}")
    }
}

internal class MyTask : Runnable {
    override fun run() {
        println("thread run 111---- ${Thread.currentThread().name} ")
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        println("thread run 111---- ${Thread.currentThread().name} " + "执行完毕")

        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Logger.d("handler thread-----${Thread.currentThread().name}")
        }

    }
}