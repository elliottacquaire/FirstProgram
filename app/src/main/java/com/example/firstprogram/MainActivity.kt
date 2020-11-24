package com.example.firstprogram

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.firstprogram.autosize.AutoSizeActivity
import com.xiaomai.environmentswitcher.EnvironmentSwitchActivity
import com.xiaomai.environmentswitcher.EnvironmentSwitcher
import com.xiaomai.environmentswitcher.bean.EnvironmentBean
import com.xiaomai.environmentswitcher.bean.ModuleBean
import com.xiaomai.environmentswitcher.listener.OnEnvironmentChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , View.OnClickListener, OnEnvironmentChangeListener {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //环境切换 -- 添加监听事件
        EnvironmentSwitcher.addOnEnvironmentChangeListener(this);

        btn_service.setOnClickListener(this)
        btn_javaDesign.setOnClickListener(this)
        dispatch_touch_event.setOnClickListener(this)
        module.setOnClickListener(this)
        env_change.setOnClickListener(this)
        autosize.setOnClickListener(this)
        annotation.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_service -> {
                val intent = Intent(this, ServicesActivity().javaClass)
                startActivity(intent)
            }
            R.id.btn_javaDesign -> {
                val intent = Intent(this, JavaDesignActivity().javaClass)
                startActivity(intent)
            }
            R.id.dispatch_touch_event -> {
                val intent = Intent(this, DispatchTouchEventActivity().javaClass)
                startActivity(intent)

                //配合activity启动模式singleTask，可启动一次，重复调用 onNewIntent()
                Handler().postDelayed({
                    ARouter.getInstance().build("/test/activity")
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation()
                },2000)

                Handler().postDelayed({
                    ARouter.getInstance().build("/test/activity")
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        .navigation()
                },3000)

                ARouter.getInstance().build("/test/activity")
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation(this)


            }
            R.id.module -> {
//                val intent = Intent(this,MainActivity().javaClass)
//                startActivity(intent)

//                ARouter.getInstance().build("/test/activity")
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    .withInt("flowOrderId", flowOrderId)
//                    .withInt("isHistory", isHistory)
//                    .withInt("flowOrderType", mFlowOrderType)
//                    .withString("orderNo", mOrderNo)
//                    .navigation(this)


                //退出应用  重启app
                val intent = baseContext.packageManager.getLaunchIntentForPackage(
                    baseContext.packageName
                )
                val restartIntent: PendingIntent = PendingIntent.getActivity(
                    applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT
                )
                val mgr: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                mgr.set(
                    AlarmManager.RTC,
                    System.currentTimeMillis() + 2000,
                    restartIntent
                ) // 1秒钟后重启应用
                System.exit(0)

            }
            R.id.env_change -> {
                EnvironmentSwitchActivity.launch(this)
            }
            R.id.autosize  -> {
                val intent = Intent(this, AutoSizeActivity().javaClass)
                startActivity(intent)
            }
            R.id.annotation -> {
                val intent = Intent(this, AnnotationActivity().javaClass)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除监听事件
        EnvironmentSwitcher.removeOnEnvironmentChangeListener(this);
    }

    override fun onEnvironmentChanged(
        module: ModuleBean?,
        oldEnvironment: EnvironmentBean?,
        newEnvironment: EnvironmentBean?
    ) {
        Log.e(
            TAG,
            module?.getName() + "由" + oldEnvironment?.getName() + "环境，Url=" + oldEnvironment?.getUrl()
                    + ",切换为" + newEnvironment?.getName() + "环境，Url=" + newEnvironment?.getUrl()
        )
    }
}