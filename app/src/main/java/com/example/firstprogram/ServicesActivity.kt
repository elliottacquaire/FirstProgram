package com.example.firstprogram

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.example.firstprogram.ipc.StudentBean
import com.example.firstprogram.ipc.StudentBeanAidlInterface
import com.example.firstprogram.service.MessengerService
import com.example.firstprogram.service.MyService
import com.example.firstprogram.service.MyService.MyBinder
import com.example.firstprogram.service.MyWork
import com.example.firstprogram.service.YourService
import kotlinx.android.synthetic.main.activity_services.*
import java.util.concurrent.TimeUnit


class ServicesActivity : AppCompatActivity(), View.OnClickListener {

    private var myBinder: MyBinder? = null
    var mBound = false //一开始，并没有和Service绑定.这个参数是用来显示绑定状态

    private val connection = object : ServiceConnection {

        //和服务绑定成功后，服务会回调该方法
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            Log.d("service", "service connected---${p0?.packageName}")
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

        msagger.setOnClickListener(this)
        aidl.setOnClickListener(this)

        click.setOnClickListener(this)

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

                val data = Data.Builder().putString("putData", "输入数据--putdata")
                    .build()  //创建需要传入的数据,注意不支持序列化数据传入

                //PeriodicWorkRequest  周期性Work请求  ;  OneTimeWorkRequest     一次性Work请求
                val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .setInputData(data)
                    .build()

                //约束条件
                val constraints: Constraints = Constraints.Builder()
                    .setRequiresDeviceIdle(true) //触发时设备是否为空闲
                    .setRequiresCharging(true) //触发时设备是否充电
                    .setRequiredNetworkType(NetworkType.UNMETERED) //触发时网络状态
                    .setRequiresBatteryNotLow(true) //指定设备电池是否不应低于临界阈值
                    .setRequiresStorageNotLow(true) //指定设备可用存储是否不应低于临界阈值
//                    .addContentUriTrigger(myUri, false) //指定内容{@link android.net.Uri}时是否应该运行{@link WorkRequest}更新
                    .build()
                // 第二个参数是重复触发的时间，第三个参数是单位
                val periodicWorkRequest = PeriodicWorkRequest.Builder(
                    MyWork::class.java,
                    15,
                    TimeUnit.MINUTES
                ).setConstraints(constraints)
                    .build()

                WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
//                WorkManager.beginWith(request1, request2).then(request3).enqueue()//链式执行

                WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                    .observe(
                        this,
                        { t ->
                            when (t?.state) {
                                WorkInfo.State.RUNNING -> {
                                    Log.d("worker", "RUNNING")
                                }
                                WorkInfo.State.CANCELLED -> {
                                    Log.d("worker", "CANCELLED")
                                }
                                WorkInfo.State.SUCCEEDED -> {
                                    Log.d("worker", "SUCCEEDED---${t.outputData.getString("data")}")
                                }
                                WorkInfo.State.FAILED -> {
                                    Log.d("worker", "FAILED")
                                }
                                else -> {
                                }
                            }
                        })

            }

            R.id.msagger -> {
                val bindIntent = Intent(this, MessengerService::class.java)
                bindService(bindIntent, mConnection, BIND_AUTO_CREATE)
            }
            R.id.aidl -> {
                val intent = Intent()
                intent.component = ComponentName(
                    packageName,
                    "com.example.firstprogram.service.MyAidlService"
                )

                bindService(intent, serviceConnection, BIND_AUTO_CREATE)

//                myAidlInterface?.let {
//                    unbindService(remoteCon)
//                    myAidlInterface = null
//                }
            }

            R.id.click -> {
                myAidl.addStudentBeanIn(StudentBean("xyz", 100))

                var list: List<StudentBean> = myAidl.studentBeanList
                for (bean in list) {
                    Log.d("MyAidlService", bean.name)
                    Log.d("MyAidlService", bean.grade.toString())
                }
                testview.text = "dddd"
            }
        }
    }

    private lateinit var myAidl: StudentBeanAidlInterface

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d("MyAidlService", "---service connected---${name.packageName}")
            myAidl = StudentBeanAidlInterface.Stub.asInterface(service)
            sendText(1, "客户端连接成功...")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d("MyAidlService", "---service dis connected");
        }
    }


    private fun sendText(what: Int, text: String) {
        myAidl?.let {
            it.addCallback(object : ICallback.Stub() {
                override fun callback(str: String?, msg: StudentBean?) {
                    testview.text = "callback 回调:$str $msg"
                }
            })

//            val sendMsg = it.sendMsg(StudentBean("aaaaa",33))
        }
    }

    ////Messenger 进程间通信
    private var mService: Messenger? = null
    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            Log.e("TAG", "ServiceConnection-->" + System.currentTimeMillis())
            //通过服务端返回的Binder创建Messenger
            mService = Messenger(iBinder)
            //创建消息，通过Bundle传递数据
            val message: Message = Message.obtain(null, 101)
            val bundle = Bundle()
            bundle.putString("msg", "hello service,this is client")
            message.setData(bundle)
            //将客户端的Messenger对象传递给服务端
            message.replyTo = mClientMessenger;
            try {
                //向服务端发送消息
                mService?.send(message)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            Log.e("TAG", "onServiceDisconnected-->binder died")
        }
    }

    /**
     * 客户端Messenger对象
     */
    private val mClientMessenger = Messenger(MessengerHandler())
    /**
     * 用于构建客户端的Messenger对象，并处理服务端的消息
     */
    private class MessengerHandler : Handler() {
        override fun handleMessage(message: Message) {
            when (message.what) {
                102 -> Log.e(
                    "TAG",
                    "receive message from service:" + message.data.getString("msg")
                )
                else -> super.handleMessage(message)
            }
        }
    }

}