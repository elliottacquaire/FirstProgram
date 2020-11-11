package com.example.firstprogram.autosize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.firstprogram.R
import kotlinx.android.synthetic.main.activity_auto_size.*

class AutoSizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_size)

        tv_end.setOnClickListener {
            val intent = Intent(this, AutoSize1Activity().javaClass)
            startActivity(intent)

//            ARouter.getInstance().build(CoreRes.LOGIN)
//                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                .navigation()
        }
    }
}