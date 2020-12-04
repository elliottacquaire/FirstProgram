package com.example.firstprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.util.SparseArray
import android.view.View
import com.example.firstprogram.design.*
import com.example.firstprogram.handler.CustomHandler
import com.example.firstprogram.handler.CustomThread
import com.example.firstprogram.ipc.Book
import com.example.firstprogram.ipc.MODE
import com.example.firstprogram.ipc.MODE.Companion.MODE_LIST
import com.example.firstprogram.ipc.MODE.Companion.MODE_TABS
import com.example.firstprogram.ipc.PoolDateBean
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_java_design.*
import java.lang.reflect.Proxy

class JavaDesignActivity : AppCompatActivity(), View.OnClickListener {

    private var customHandler = CustomHandler()//UI线程到handler

    private var custom: CustomThread? = null

    private var latLonList = mutableListOf<PoolDateBean>()
    private var sparseArray = SparseArray<PoolDateBean>()

    private var passTime = 0

    ///////////////////////////////////////////////////////////
    private var sssdasd: String = "dddd"
        //        get() = "get test ss"  //固定值，永不变
        get() {
            return field //返回自身，可省略
        }
        set(value) {
            field = value.toUpperCase() //将传入的值，处理
//            field = value
        }

    var setterVisibility: String = "abc"
        private set

//////////////////////////////////////////////////////////////////////////////
    //创建handler
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
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
        btn_pool_object.setOnClickListener(this)
        btn_chain.setOnClickListener(this)

        setModel(MODE_TABS)
//        setModel(5)

        Log.i("aaa", "-----${customHandler.looper.thread}-------")

        custom = CustomThread("custom")
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_javaDesign -> {
//                val user = User()
//                val userProxy = UserDaoProxy(user)
//                userProxy.save()
                Log.i("aaa", "-b-------------")
//                val target = User()
//                val userTargetProxy = ProxyFactory(target).getProxyInstance() as IUserDao
//                Log.i("aaa","-btn_javaDesign---${target.javaClass}")
//                Log.i("aaa","-btn_javaDesign---${target.javaClass.classLoader}")
//                Log.i("aaa","-btn_javaDesign---${userTargetProxy.javaClass}")
//                userTargetProxy.save()

                val user = User()
                val invocationHandler = MyInvocationHandler(user)
                val javaIntad = ProxyHandler(user)
                //Class[] interfaces 表示代理类需要实现的接口，这些接口中表示需要你拦截的方法
                //比如目标类中实现了A，B两接口，但是你只需要拦截代理接口A的方法，那么这里写A接口就行了。
                for (nt in user.javaClass.interfaces) {
                    Log.i("aaa", "-btn_javaDesign---${nt.name}")
                }

                val userDor1 = Proxy.newProxyInstance(
                    user.javaClass.classLoader,
                    user.javaClass.interfaces,
                    invocationHandler
                ) as IUserDao
//                val result = userDor1.save()
                userDor1.delete("projects")
//                val userDor = Proxy.newProxyInstance(user.javaClass.classLoader,user.javaClass.interfaces,javaIntad) as IUserDao
//                val result = userDor.save()
//                userDor.delete("projects")

            }
            R.id.btn_handler -> {

                val message = Message.obtain()
                message.what = 0
                message.arg1 = 101
                customHandler.sendMessage(message)

                //三方线程通信到UI线程
                Thread {
                    Log.i("aaa", "------------")
                    val messages = Message.obtain()
                    messages.what = 0
                    messages.arg1 = 1011
//                    customHandler.sendMessage(messages)
                    customHandler.sendMessageDelayed(messages, 1000)
                }.start()

                custom?.start()
                val messages = Message.obtain()
                messages.what = 0
                messages.arg1 = 101111
                custom?.getHandler()?.sendMessage(messages)


            }
            R.id.btn_pool_object -> {
                tv_time.start()
//                latLonList.clear()
                tv_time.setOnChronometerTickListener {
                    passTime++
                    var bean = PoolDateBean.obtain()
                    bean?.id = passTime
                    bean?.name = "json_jack mouth ${passTime}"
                    bean?.let {
                        latLonList.add(it)
                    }
                    tv_time.text = "$passTime --秒"
                    Log.i("aaa", "-TIME show-------$passTime------")
                }
                btn_pool_object.isEnabled = false
            }
            R.id.btn_chain -> {
                val handler1: HandlerTest = ConcreteHandler1()
                val handler2: HandlerTest = ConcreteHandler2()
                handler1.setNext(handler2)
                //提交请求
                handler1.handleRequest("two")

                testMult()

                ///////////////////////////////////////
                sssdasd = "get hero hei"
                Logger.d(sssdasd)
                setterVisibility = "fsdf"
                Logger.d(setterVisibility)
            }


        }
    }

    private fun setModel(@MODE m: Int) {

    }

    private fun getS(): @MODE Int {
        return MODE_LIST
    }

    fun testMult() { //mutableListOf 另一种赋值方法
        val interceptors = mutableListOf<String>()
        interceptors += "One"
        interceptors += "two"
        interceptors += "three"
        interceptors += "four"
        interceptors += "five"
        interceptors += "six"
        interceptors.add("seven")
        Logger.d(interceptors)
    }
}