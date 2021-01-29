package com.example.firstprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_first.*


@Route(path = "/app/first",group = "app")
class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        btn_acitivyjump.setOnClickListener {
            val intent = Intent(this, SecondActivity().javaClass)
            startActivity(intent)
        }
    }
}