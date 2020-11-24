package com.example.firstprogram

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.firstprogram.annotation.AnimationTest
import com.example.firstprogram.annotation.MyAnnotation
import com.example.firstprogram.annotation.MyAnnotation1
import kotlinx.android.synthetic.main.activity_annotation.*

class AnnotationActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)

        clicl.setOnClickListener(this)
        click2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            /**
             * getAnnotation：返回指定的注解
            isAnnotationPresent：判定当前元素是否被指定注解修饰
            getAnnotations：返回所有的注解
            getDeclaredAnnotation：返回本元素的指定注解
            getDeclaredAnnotations：返回本元素的所有注解，不包含父类继承而来的
             */
            R.id.clicl -> {
                val animationTest = AnimationTest()
                animationTest.testAge(100)
                val ani = AnimationTest::class.java
//                val aniMethod = ani.getMethod(
//                    "somebody", Class[]{String::class.java, Int::class.java}
//                )
                val somebody = AnimationTest::class.java.getMethod("somebody",String::class.java,Int::class.java)
                val myAnnotation = somebody.getAnnotation(MyAnnotation::class.java)
                val value = myAnnotation?.value
                Log.i("aaa","-1------${value?.get(1)}")

                val methodTestAge = AnimationTest::class.java.getDeclaredMethod("testAge",Int::class.java)
                val annotationPresent = methodTestAge.isAnnotationPresent(MyAnnotation1::class.java)
                if (annotationPresent){
                    val myAnnotation1 = methodTestAge.getAnnotation(MyAnnotation1::class.java)
                    val age = myAnnotation1?.getAge
                    val ss = myAnnotation1?.value
                    Log.i("aaa","-----$age--")
                    Log.i("aaa","-----${ss?.get(0)}--")
                }


            }
            R.id.click2 -> {

            }
        }
    }
}