package com.example.firstprogram.eventbus

import android.os.Looper
import com.orhanobut.logger.Logger
import java.util.*
import java.util.concurrent.Executors


object CustomEventBus {

    final val eventTypesCache = mutableMapOf<Class<*>, List<StaockInfo>>()
    final val registList = mutableListOf<StaockInfo>()
    private val DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool()

    fun register(objects: Any){
        Logger.d("----register start----")
//        Logger.d(objects.javaClass.name)

        val subscriberClass: Class<*> = objects.javaClass
        val constructor  = subscriberClass.constructors //*获取所有的”公有“构造方法
        for (constItem in constructor) {
//            Logger.d(constItem)
        }

        val getPubMethod = subscriberClass.methods//*获取所有的”“方法 包括私有的********
//        Logger.d("size----${getPubMethod.size}")
        for (pubMethod in getPubMethod){
//            Logger.d(pubMethod)

        }
        val getPriMethod = subscriberClass.declaredMethods  //*获取所有的”公有“方法
        Logger.d("size----${getPriMethod.size}")
        for (privaMethod in getPriMethod){
//            Logger.d(privaMethod)
            val modi = privaMethod.modifiers
//            if ((modi & Modifier.PUBLIC) != 0 ){

//            }
//            Logger.d(modi)

            val parameterTypes = privaMethod.parameterTypes
            if (parameterTypes.size == 1){
                val isSubscribeAnnotation = privaMethod.isAnnotationPresent(Subscrobe::class.java)
                if (isSubscribeAnnotation){
                    val subscribeAnnotation = privaMethod.getAnnotation(Subscrobe::class.java)
                    val eventType = parameterTypes[0]
                    val param = subscribeAnnotation?.threadModel
                    val isStick = subscribeAnnotation?.isThicky?:false
                    val isMain = param == "main"
                    Logger.d("--$eventType-------$param----$subscribeAnnotation--")
//                    privaMethod.invoke(objects,CustomEven(30,"Jacek Linda"))

                    registList.add(
                        StaockInfo(
                            objects,
                            privaMethod.name,
                            privaMethod,
                            isMain,
                            isStick
                        )
                    )
                }
            }
        }
        eventTypesCache[subscriberClass] = registList

    }

    fun unRegister(objects: Any){
        Logger.d("----unregister end----")
        registList.clear()
        eventTypesCache.clear()
    }

    fun postEvent(isStack: Boolean){
        var customEven : CustomEven? = null
        customEven = if (isStack){
            CustomEven(20, "test1")

        }else{
            CustomEven(30, "test2")
        }

        val isMain = Looper.myLooper() == Looper.getMainLooper()

        /**
         * 仿eventbus ，在post的时候，判断当前线程类型，进行比较，
         * 然后进行线程切换，同时，遍历获取发送事件的方法所在地，然后在新线程中反射调用，把参数传递过去。
         */

        Logger.d("----$isMain------${Thread.currentThread()}----------")
        if (eventTypesCache.isNotEmpty()){
            val stock = registList[0]
            val obj = registList[0].objec
            registList[0].method.invoke(obj, customEven) //反射调用activity内的有注解的对应方法
        }

    }
}