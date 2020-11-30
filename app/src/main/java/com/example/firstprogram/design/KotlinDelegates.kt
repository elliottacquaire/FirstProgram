package com.example.firstprogram.design

import kotlin.reflect.KProperty

/**
 * kotlin实现类代理非常简单，通过关键字by就可以让b代理自己的方法，b将会在Derived中内部存储，并且编译器将生成转发给b的所有 Base的方法
 */
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

class Derived(b: Base) : Base by b

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    Derived(b).print() // 输出 10
}

/**
 * 属性代理，我们用到的较多的有lazy、Delegates.notNull()、Delegates.observable()等，这些都是kotlin自带的一些标准代理，
 *  miss 'getValue(mainActivity: MainActivity, property: KProperty<*>): Any' method on delegate of type 'Delegate'
 *  这 如果我们把p前面改成var,那么编译器又会提示一个需要setValue方法
 */
class Example {
//    val p: String by Delegate()
    var p: String by Delegate()
}

class Delegate {
    private var mRealValue = ""

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return mRealValue
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        mRealValue = value
    }
}
