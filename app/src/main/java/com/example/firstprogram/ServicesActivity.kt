package com.example.firstprogram

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.firstprogram.service.MyService
import com.example.firstprogram.service.MyService.MyBinder
import com.example.firstprogram.service.MyWork
import com.example.firstprogram.service.YourIntentService
import com.example.firstprogram.service.YourService
import kotlinx.android.synthetic.main.activity_services.*
import java.util.concurrent.TimeUnit


class ServicesActivity : AppCompatActivity(), View.OnClickListener {

    private var myBinder: MyBinder? = null
    var mBound = false //一开始，并没有和Service绑定.这个参数是用来显示绑定状态

    private val connection = object : ServiceConnection {

        //和服务绑定成功后，服务会回调该方法
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            Log.d("service", "service connected");
            myBinder = service as MyBinder
            myBinder?.startDown()
            mBound = true  //true说明是绑定状态
        }

        //当服务异常终止时会调用。注意，解除绑定服务时不会调用
        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d("service", "service dis connected");
            mBound = false; //服务异常终止时，状态为未绑定
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        /**
         * 点击Stop Service按钮只会让Service停止，点击Unbind Service按钮只会让Service和Activity解除关联，
         * 一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁。
         */
        button1_start_service.setOnClickListener(this)
        button2_stop_service.setOnClickListener(this)
        button3_bind_service.setOnClickListener(this)
        button4_unbind_service.setOnClickListener(this)

        jobservice.setOnClickListener(this)
        WorkManagers.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button1_start_service -> {
                val startIntent = Intent(this, MyService().javaClass)
                startService(startIntent)
            }
            R.id.button2_stop_service -> {
                val startIntent = Intent(this, MyService().javaClass)
                stopService(startIntent)
            }
            R.id.button3_bind_service -> {
                val bindIntent = Intent(this, MyService::class.java)
                bindService(bindIntent, connection, BIND_AUTO_CREATE)
            }
            R.id.button4_unbind_service -> {
                if (mBound) {
                    unbindService(connection)
                    mBound = false
                }

            }
            R.id.jobservice -> {
                //start jobIntentService
//                val startIntent = Intent()
//                startIntent.putExtra("work", "work num:")
//                YourIntentService.enqueueWork(this, startIntent)

                //start jobService
//                val scheduler1: JobScheduler = this.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                val scheduler: JobScheduler = getSystemService(JobScheduler::class.java)
                val builder = JobInfo.Builder(
                    100,
                    ComponentName(this, YourService::class.java)
                )

                builder.setOverrideDeadline(2000)
                scheduler.schedule(builder.build())


//                scheduler.cancel(100)
            }
            R.id.WorkManagers -> {

                val data = Data.Builder().putString("putData","输入数据").build()  //创建需要传入的数据,注意不支持序列化数据传入

                val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .setInputData(data)
                    .build()

                WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
            }
        }
    }
}