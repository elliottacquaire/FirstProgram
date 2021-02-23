package com.example.firstlibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselib.bean.BaseBusEvent
import kotlinx.android.synthetic.main.activity_first_main.*
import org.greenrobot.eventbus.EventBus

/**
 * Arouter 原理  apt是在编译期对代码中指定的注解进行解析，然后做一些其他处理（如通过javapoet生成新的Java文件）
 * 我们在代码里加入的@Route注解，会在编译时期通过apt生成一些存储path和activity.class映射关系的类文件，
 * 然后app进程启动的时候会加载这些类文件，把保存这些映射关系的数据读到内存里(保存在map里)，
 * 然后在进行路由跳转的时候，通过build()方法传入要到达页面的路由地址，
 * ARouter会通过它自己存储的路由表找到路由地址对应的Activity.class(activity.class = map.get(path))，
 * 然后new Intent(context, activity.Class)，当调用ARouter的withString()方法它的内部会调用intent.putExtra(String name, String value)，
 * 调用navigation()方法，它的内部会调用startActivity(intent)进行跳转，这样便可以实现两个相互没有依赖的module顺利的启动对方的Activity了。
 */

@Route(path = "/first/firstA",group = "first")
class FirstMainActivity : AppCompatActivity()  , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_main)

        poser.setOnClickListener(this)
        pos.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.poser -> {

//                val intent = Intent(this, ServicesActivity().javaClass)
//                startActivity(intent)
                ARouter.getInstance().build("/test/activity")
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    .navigation(this)


            }
            R.id.pos -> {
                Log.d("aaa","module eventbus start")
                EventBus.getDefault().postSticky(BaseBusEvent())
            }
        }
    }
}