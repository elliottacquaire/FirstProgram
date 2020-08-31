package com.example.firstprogram.service

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import com.example.firstprogram.ICallback
import com.example.firstprogram.ipc.StudentBean
import com.example.firstprogram.ipc.StudentBeanAidlInterface


/*
* a. 所有基础类型（int, char, 等）

b. String，List，Map，CharSequence等类

c. 其他AIDL接口类型

d. 所有Parcelable的类
* */
class MyAidlService : Service()  {

    private lateinit var studentArrayList: ArrayList<StudentBean>

    override fun onCreate() {
        super.onCreate()
        Log.d("MyAidlService", "onCreate");
        studentArrayList = ArrayList()
        initData()
    }

    private fun initData() {
        var studentBean1 = StudentBean("fly1", 21)
        var studentBean2 = StudentBean("fly2", 22)
        var studentBean3 = StudentBean("fly3", 23)
        var studentBean4 = StudentBean("fly4", 24)

        studentArrayList.add(studentBean1)
        studentArrayList.add(studentBean2)
        studentArrayList.add(studentBean3)
        studentArrayList.add(studentBean4)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyAidlService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyAidlService", "onDestroy");
    }


    override fun onBind(intent: Intent?): IBinder? {
        //通过intent，客户端可以给server传递消息
        //通过intent，客户端可以给server传递消息
        val info = intent!!.getStringExtra("extra")
        Log.d("MyAidlService", "info=$info")
        Log.d(
            "MyAidlService",
            "onBind" + " action=" + intent!!.action + " package=" + intent!!.getPackage()
        )
        val check = checkCallingOrSelfPermission("com.example.firstprogram.permission.LOCAL")
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.d("MyAidlService", "client does not get permission")
            return null
        }
        return iBinder
    }

    private val iBinder = object : StudentBeanAidlInterface.Stub() {

        override fun getStudentBeanList(): MutableList<StudentBean> {
            return studentArrayList
        }

        override fun addStudentBeanInOut(bean: StudentBean?) {
            if (bean != null) {
                studentArrayList.add(bean)
            }
        }

        override fun addStudentBeanIn(bean: StudentBean?) {
            if (bean != null) {
                studentArrayList.add(bean)
            }
//            throw Throwable("test exception")
        }

        override fun addStudentBeanOut(bean: StudentBean?) {
            if (bean != null) {
                studentArrayList.add(bean)
            }
        }

        override fun addCallback(callback: ICallback?) {
            this@MyAidlService.callback = callback
        }

    }


    /*用来和客户端通信的回调*/
    var callback: ICallback? = null

    private fun sendText(what: Int, text: String) {
        callback?.callback("服务端回调:", StudentBean("ttttttt", 21))
    }


}