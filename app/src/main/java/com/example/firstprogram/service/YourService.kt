package com.example.firstprogram.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.HandlerThread
import android.util.Log

class YourService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d("YourService","onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("YourService","onDestroy")
    }

    //如果返回false的话，系统会自动结束本job；
    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("YourService","onStartJob--${p0?.jobId}")
        //执行实际的工作
        //onStartJob在主线程中调用，因此耗时工作需要利用独立线程来完成
        //任务完成后，调用jobFinished接口
        //第二个参数的描述是：needsReschedule
        //当为true时，表示当前任务需要下次重新安排
        jobFinished(p0, false)
        return true
    }

    //对于自行cancel了的Job无效。JobService不论何种原因被停止了都希望能自动启动的话，需要在onStopJob()或
    //onDestroy()里强制再次schedule我们的jobservice。
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("YourService","onStopJob--${p0}")
        return true
    }


}