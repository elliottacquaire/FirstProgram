package com.example.firstprogram.design

import android.util.Log
import java.lang.reflect.Proxy


class ProxyFactory(targer: Any)  {

    //维护一个目标对象
    private var target: Any? = null
    init {
        this.target = targer
    }

    fun getProxyInstance() : Any {
        return Proxy.newProxyInstance(
            target?.javaClass?.classLoader, //获得实际对象所属类的类加载器
            target?.javaClass?.interfaces   //获得实际对象实现的全部接口.obj是实际对象
        ) { proxy, method, args ->
            //执行目标对象方法
            Log.i("aaa", "-getProxyInstance---")
            println("MyInvocationHandler invoke begin")
            Log.i("aaa","proxy: " + proxy.javaClass.name)
            Log.i("aaa","method: " + method.name)
            for (o in args) {
                println("arg: $o")
            }
            //通过反射调用 被代理类的方法

            try {
                val returnValue = method.invoke(target, args)
                return@newProxyInstance returnValue
            }catch (e: Exception){

            }

        }
    }
}