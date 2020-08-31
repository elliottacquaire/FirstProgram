package com.example.firstprogram.service

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    //任务是交给系统统一调度的，适合一些轻量级的后台功能使用。还能支持在Doze模式下运行后台任务
    //使用WorkManager取代所有周期或者长时间的后台工作是必需必要的
    override fun doWork(): Result {
        //获取传递过来的值，注意不支持序列化数据传入
        val dataIn = inputData.getString("putData")
        Log.e("worker", "this_doWork---$dataIn");
//        return Result.success();//结果返回为成功

        //成功与失败的结果还能携带数据返回。
        val dataOut: Data = Data.Builder().putString("data", "返回数据--data -- success").build()
        return Result.success(dataOut)
    }
}