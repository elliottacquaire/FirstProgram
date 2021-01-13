package com.example.firstprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_custom_view.*

class CustomViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        Logger.d("create-------")
        val sss = "33004dksfefwen这个了师傅给家啦阿里放假啊诶家饿哦肌肤,卡洛斯的肌肤建设.路附近fasffalfje非v啊疯了；fje放假啊；dfaf阿嘎减肥i俄飞机" +

                "egfaffaslfjsldfjslf带ID发i发诶放假啦上/?飞机IE放假额外1!"

        val  ss = ""

        mulTxt.setTextView(ss)

    }
}