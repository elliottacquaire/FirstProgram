package com.example.firstprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_dispatch_touch_event.*


class DispatchTouchEventActivity : AppCompatActivity() , View.OnClickListener {

    val TAG = "touch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_touch_event)

        lin_green.setOnClickListener(this)
        img.setOnClickListener(this)

//        lin_green.touc
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG,"-----dispatchTouchEvent-------")
        return super.dispatchTouchEvent(ev)
//        return true
//        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG,"-----onTouchEvent-------")
        return super.onTouchEvent(event)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.lin_green -> {

            }
            R.id.img -> {

            }
        }
    }

}