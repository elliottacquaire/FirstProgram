package com.example.firstprogram.BroadcastReceiver

import com.alibaba.android.arouter.launcher.ARouter

/**
 * Arouter 推送 把信息传递到 标记 处
 */
object MessageManager {

    val messageARouter: IPorscheMessageReceiver = ARouter.getInstance().build("/main/push")
        .navigation() as IPorscheMessageReceiver

}