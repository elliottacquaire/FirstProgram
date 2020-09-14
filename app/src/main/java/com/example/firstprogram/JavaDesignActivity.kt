package com.example.firstprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import com.example.firstprogram.design.*
import com.example.firstprogram.handler.CustomHandler
import com.example.firstprogram.handler.CustomThread
import kotlinx.android.synthetic.main.activity_java_design.*
import java.lang.reflect.Proxy

class JavaDesignActivity : AppCompatActivity(), View.OnClickListener {

    private var customHandler =  CustomHandler()//UI线程到handler

    private var custom : CustomThread? = null

    //创建handler
    val  handler = object: Handler() {
        override fun handleMessage(msg : Message) {
            super.handleMessage(msg);
            if (msg.what == 0x11) {
                //更新ui

            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java_design)

        btn_javaDesign.setOnClickListener(this)
        btn_handler.setOnClickListener(this)

        Log.i("aaa","-----${customHandler.looper.thread}-------")

        custom = CustomThread("custom")
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_javaDesign -> {
//                val user = User()
//                val userProxy = UserDaoProxy(user)
//                userProxy.save()
                Log.i("aaa","-b-------------")
//                val target = User()
//                val userTargetProxy = ProxyFactory(target).getProxyInstance() as IUserDao
//                Log.i("aaa","-btn_javaDesign---${target.javaClass}")
//                Log.i("aaa","-btn_javaDesign---${target.javaClass.classLoader}")
//                Log.i("aaa","-btn_javaDesign---${userTargetProxy.javaClass}")
//                userTargetProxy.save()

                val user = User()
                val invocationHandler = MyInvocationHandler(user)
                for (int in user.javaClass.interfaces){
                    Log.i("aaa","-btn_javaDesign---${int.name}")
                }

                val userDor = Proxy.newProxyInstance(user.javaClass.classLoader,user.javaClass.interfaces,invocationHandler) as IUserDao
                userDor.save()
//                userDor.delete("projects")




            }
            R.id.btn_handler -> {

                val message = Message.obtain()
                message.what = 0
                message.arg1 = 101
                customHandler.sendMessage(message)

                //三方线程通信到UI线程
                Thread {
                    Log.i("aaa","------------")
                    val messages = Message.obtain()
                    messages.what = 0
                    messages.arg1 = 1011
//                    customHandler.sendMessage(messages)
                    customHandler.sendMessageDelayed(messages,1000)
                }.start()

                custom?.start()
                val messages = Message.obtain()
                messages.what = 0
                messages.arg1 = 101111
                custom?.getHandler()?.sendMessage(messages)


            }
        }
    }
}