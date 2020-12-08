package com.example.firstprogram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_third.*

/**
 * singleInstance:singleInstance模式下，会将打开的Activity压入一个新的任务栈中。
 * 例如：Task栈1中结构为：A B C,C通过Intent跳转到了D（D的模式为singleInstance），
 * 那么则会新建一个Task，栈1中结构依旧为A B C,栈2中结构为D。此时屏幕显示D，之后D通过Intent跳转到D，
 * 栈2不会压入新的D，所以两个栈中的情况没发生改变。如果D跳转到了C，那么就会根据C对应的launchMode在栈1中进行对应的操作，C如果为standard，
 * 那么D跳转到C，栈1的结构为A B C C ,此时点击返回按钮，还是在C，栈1的结构变为A B C,而不会回到D。最后task栈1完全关闭后，栈2显示出来，此时点击返回按钮，全都结束。

 */
class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        btn_acitivyjump.setOnClickListener {
//            val intent = Intent(this, ThirdActivity().javaClass)
            val intent = Intent(this, SecondActivity().javaClass)

            startActivity(intent)
        }
        Logger.d("third---"+this.taskId+this.packageName)
    }
}