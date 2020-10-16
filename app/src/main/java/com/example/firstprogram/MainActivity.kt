package com.example.firstprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_service.setOnClickListener(this)
        btn_javaDesign.setOnClickListener(this)
        dispatch_touch_event.setOnClickListener(this)
        module.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_service -> {
                val intent = Intent(this,ServicesActivity().javaClass)
                startActivity(intent)
            }
            R.id.btn_javaDesign -> {
                val intent = Intent(this,JavaDesignActivity().javaClass)
                startActivity(intent)
            }
            R.id.dispatch_touch_event -> {
                val intent = Intent(this,DispatchTouchEventActivity().javaClass)
                startActivity(intent)
            }
            R.id.module -> {
//                val intent = Intent(this,MainActivity().javaClass)
//                startActivity(intent)

                ARouter.getInstance().build("/test/activity")
//                    .withInt("flowOrderId", flowOrderId)
//                    .withInt("isHistory", isHistory)
//                    .withInt("flowOrderType", mFlowOrderType)
//                    .withString("orderNo", mOrderNo)
                    .navigation(this)

            }
        }
    }
}