package com.example.firstprogram.service

import android.R.id
import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log


/**
 * 使用Messager来传递Message，Message中能使用的字段只有what、arg1、arg2、Bundle和replyTo,自定义的Parcelable对象无法通过object字段来传输
Message中的Bundle支持多种数据类型，replyTo字段用于传输Messager对象，以便进程间相互通信
Messager以串行的方式处理客户端发来的消息，不适合有大量并发的请求
Messager方法只能传递消息，不能跨进程调用方法

 */

class MessengerService : Service() {
    private val TAG = "MessagerService"

    /**
     * 处理来自客户端的消息，并用于构建Messenger
     */
    private class MessengerHandler : Handler() {

        override fun handleMessage(message: Message) {
            when (message.what) {
                101 -> {
                    Log.i(
                        "TAG",
                        "receive message from client:" + message.getData().getString("msg")
                    )
                    //获取客户端传递过来的Messenger，通过这个Messenger回传消息给客户端

                    val client: Messenger = message.replyTo
                    //当然，回传消息还是要通过message
                    val msg: Message = Message.obtain(null, 102)
                    val bundle = Bundle()
                    bundle.putString("msg", "hello client, I have received your message!")
                    msg.data = bundle
                    try {
                        client.send(msg)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                else -> super.handleMessage(message)
            }
        }
    }

    /**
     * 构建Messenger对象
     */
    private val mMessenger: Messenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder? {
        //将Messenger对象的Binder返回给客户端
        return mMessenger.binder
    }
}