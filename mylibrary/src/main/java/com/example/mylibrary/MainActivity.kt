package com.example.mylibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselib.bean.BaseBusEvent
import com.example.mylibrary.downManager.FileDownloaderUtil
import com.liulishuo.filedownloader.FileDownloader
import kotlinx.android.synthetic.main.activity_mains.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.logging.Logger


// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/test/activity",group = "test")
class MainActivity : AppCompatActivity()  , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mains)

        EventBus.getDefault().register(this)
        Log.d("aaa","module activity start")
        downLoad.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.downLoad -> {
                FileDownloader.setup(this)
//                val intent = Intent(this, ServicesActivity().javaClass)
//                startActivity(intent)
                FileDownloaderUtil.startDown("tttt","filename")

            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("aaaa","onNewIntent")
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun onReMsg(message : BaseBusEvent) {
        Log.d("aaa","module eventbus value is: $message")
    }
}