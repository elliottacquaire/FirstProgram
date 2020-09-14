package com.example.firstprogram.design

import android.util.Log
import java.lang.Exception
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method


class MyInvocationHandler(sObject: Any) : InvocationHandler {

    private val sObject : Any = sObject

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {

        Log.i("aaa","proxy: " + proxy?.javaClass?.canonicalName)
        Log.i("aaa","method: " + method?.name)
        Log.i("aaa","sObject: " + sObject?.javaClass?.canonicalName)
        Log.i("aaa","time-start--${System.currentTimeMillis()}")
        if (args != null) { //输入参数
            for (o in args) {
                Log.i("aaa","---arg: $o") //---projects
            }
        }
        //通过反射调用 被代理类的方法
        try {
            method?.invoke(sObject, null)
//            method?.invoke(sObject, args)
//            method?.invoke(sObject, args?.get(0))//一个参数

        }catch (e : Exception){
            Log.i("aaa","---$e--")
        }
        Log.i("aaa","time-end--${System.currentTimeMillis()}")
        Log.i("aaa","MyInvocationHandler invoke end")
        return null

    }
}