package com.example.firstprogram.design

import android.util.Log
import com.orhanobut.logger.Logger
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method


class MyInvocationHandler(sObject: Any) : InvocationHandler {

    private val sObject : Any = sObject

    /**
     * Java 反射机制 反射数组 wrong number of arguments 异常
     * 因为编译器会把字符串数组当作一个可变长度参数传给对象o,而我们取得方法只有一个参数,
     * 所以就会出现wrong number of arguments的异常,
     * 我们只要把字符串数组强制转换为一个Object对象就可以解决这个异常了.
     */
    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {

        Log.i("aaa", "proxy: " + proxy?.javaClass?.canonicalName)
        Log.i("aaa", "method: " + method?.name)
        Log.i("aaa", "sObject: " + sObject?.javaClass?.canonicalName)
        Log.i("aaa", "time-start--${System.currentTimeMillis()}")
        Logger.d("---11111-------$args")
        var invoke : Any? = null

        if (!args.isNullOrEmpty()) { //输入参数
            for (o in args) {
                Log.i("aaa", "---arg: $o") //---projects
            }
        }
        //通过反射调用 被代理类的方法
        try {
            Log.i("aaa", "---start : try catch")
//            method?.invoke(sObject, null)
//             invoke = method.invoke(sObject, args)

            invoke = method.invoke(sObject, *args.orEmpty())
//            method?.invoke(sObject, args?.get(0))//一个参数
            Log.i("aaa", "---issss: $invoke")
        }catch (e: Exception){
            Log.i("aaa", "---$e--")
        }
        Log.i("aaa", "time-end--${System.currentTimeMillis()}")
        Log.i("aaa", "MyInvocationHandler invoke end")
        return invoke

    }

}