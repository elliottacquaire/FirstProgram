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