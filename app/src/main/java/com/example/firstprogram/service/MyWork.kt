package com.example.firstprogram.service

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
//        val data = inputData.getString("putData")
//        Log.e("MyWork", "this_doWork---$data");
//        return Result.success();//结果返回为成功

        //成功与失败的结果还能携带数据返回。
        val data: Data = Data.Builder().putString("data", "返回数据").build()
        return Result.success(data)
    }
}