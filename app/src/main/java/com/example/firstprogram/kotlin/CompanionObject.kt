package com.example.firstprogram.kotlin

import android.content.ContentValues.TAG
import android.util.Log
import com.orhanobut.logger.Logger

/**
 * object 关键字可以表达两种含义：一种是对象表达式,另一种是 对象声明。
 * companion object 修饰为伴生对象,伴生对象在类中只能存在一个，类似于java中的静态方法 Java 中使用类访问静态成员，静态方法。
 *
 * 用object 修饰的类为静态类，里面的方法和变量都为静态的。
 * 类内部的对象声明，没有被inner 修饰的内部类都是静态的
 *
 * 1为什么companion object 中调用不到外部成员变量,在java中我们写一个静态方法，如果需要调用成员变量，是无法调用到的,
 * 只有将 TAG 修改为静态成员变量才能调用到,
 * java中静态方法调用成员变量，要求成员变量必须是静态的， 在kotlin 中也是一样，所以当companion object 中调用非静态的成员变量也是调用不到的
 * companion object表示 伴生对象")}}将所引用的成员变量也修饰静态的，这样就可以引用到了
 */
object DemoManagera{
    val tt = "sdfs"
    fun myObject() {
        fun a() {
            Log.e(TAG,"此时 object 表示 ")
        }
    }
    //没有被inner 修饰的内部类都是静态的
    fun myObjectsss() {
        fun a() {
            Log.e(TAG,"此时 objects 表示 ")
        }
    }

}

class DemoManager{
    private val bar: Int = 1
    object MyObject {
        fun a() {
            Log.e(TAG,"此时 object 表示 直接声明类")
        }
    }

    inner class MyObjects {
        fun a() {
            Log.e(TAG,"此时 object 表示 直接声明类")
        }
        //内部类可以访问外部类成员,可看作外部类对象的一个成员
        fun foo() = bar
    }
}

class CompanionObject {
    private val ssd = "fsdfsa"
    val isEmpty: Boolean
        get() = this.ssd.isEmpty()

    private var sssdasd : String
        get() = "get test ss"
        set(value) {
            value.toUpperCase()
        }

    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation


    fun main(args: Array<String>) {
        // 调用
        DemoManagera.tt
        DemoManagera.myObject()
        DemoManagera.myObjectsss()

        //
        DemoManager.MyObject.a()
        DemoManager().MyObjects().a()

        //
        StaticDemoActivity4.test()
        StaticDemoActivity3.test()

        //
        sssdasd = "get hero hei"
        Logger.d(sssdasd)
        setterVisibility = "fsdf"
    }

}

//kotlin 常量
//const 关键字用来修饰常量，且只能修饰 val，不能修饰var, companion object 的名字可以省略，可以使用 Companion来指代
class StaticDemoActivity {
    companion object {
        val  LOAN_TYPE = "loanType"
        val  LOAN_TITLE = "loanTitle"
    }
}

//或者

class StaticDemoActivity1 {
    companion object StaticParams{
        val  LOAN_TYPE = "loanType"
        val  LOAN_TITLE = "loanTitle"
    }
}

//或者
class StaticDemoActivity2 {
    companion object {
        const val LOAN_TYPE = "loanType"
        const val LOAN_TITLE = "loanTitle"
    }
}

/////////////java/静态方法/////////////////////

//class StaticDemoActivity {
//    public static void test(){
//    }
//}
class StaticDemoActivity4 {
    companion object {
        fun test(){
        }
    }
}

//或者

class StaticDemoActivity3 {
    companion object StaticParams{
        fun test() {
        }
    }
}


