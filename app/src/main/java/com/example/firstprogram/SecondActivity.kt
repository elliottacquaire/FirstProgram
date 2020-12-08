package com.example.firstprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btn_acitivyjump.setOnClickListener {
            val intent = Intent(this, ThirdActivity().javaClass)
            startActivity(intent)
        }
        Logger.d("second---"+this.taskId+this.packageName)
    }
}