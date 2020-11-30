package com.example.firstprogram.kotlin

import com.example.firstprogram.design.BaseImpl
import com.example.firstprogram.design.Derived
import java.util.concurrent.locks.Lock

/**
 * kotlin 语言中的高级操作
 */


class KotlinTest {
    //无参数的情况
    // 源代码
    fun test(){ println("无参数") }

    // lambda代码
    val test = { println("无参数") }

    ////////////有参数情况////////////////////////
    // 源代码
    fun test(a : Int , b : Int) : Int{
        return a + b
    }
    // lambda
    val test1 : (Int , Int) -> Int = {a , b -> a + b}
    // 或者
    val test11 = {a : Int , b : Int -> a + b}

    //////////////////////lambda表达式作为函数中的参数的时候//////////////////////////

    fun sum(num1 : Int , num2 : Int) : Int{
        return num1 + num2
    }

    // lambda
    fun test(a : Int , b : (num1 : Int , num2 : Int) -> Int) : Int{
        return a + b.invoke(3,5)
    }

    /**
     * it并不是Kotlin中的一个关键字(保留字)。
    it是在当一个高阶函数中Lambda表达式的参数只有一个的时候可以使用it来使用此参数。it可表示为单个参数的隐式名称，是Kotlin语言约定的。
    可以用下划线(_)表示未使用的参数，表示不处理这个参数。
    匿名函数的特点是可以明确指定其返回值类型。
    它和常规函数的定义几乎相似。他们的区别在于，匿名函数没有函数名。

    data = " " // this is a text with blank space
    println(data.isNullOrBlank()?.toString())  //true
    println(data.isNullOrEmpty()?.toString())  //false
    Evils操作符
    ?: 这个操作符表示在判断一个可空类型时，会返回一个我们自己设定好的默认值.
    !! 这个操作符表示在判断一个可空类型时，会显示的抛出空引用异常（NullPointException）.
    as? 这个操作符表示为安全的类型转换.
    当使用as操作符的使用不能正常的转换的情况下会抛出类型转换（ClassCastException）异常，而使用as?操作符则会返回null,但是不会抛出异常
    :: 双冒号操作符 表示把一个方法当做一个参数，传递到另一个方法中进行使用，通俗的来讲就是引用一个方法
    inline fun <reified T> T.foo3(string: String) {
    Log.e(T::class.simpleName, string)
    }

    @ 限定this的类型
    class User {
    inner class State{
    fun getUser(): User{
    //返回User
    return this@User
    }
    fun getState(): State{
    //返回State
    return this@State
    }
    }
    }

    ..有区间的概念，区间表达式由具有操作符形式 .. 的 rangeTo 函数辅以 in 和 !in 形成。 区间是为任何可比较类型定义的
    if (i in 1..10) { // 等同于 1 <= i && i <= 10 (1 <= i <= 10)
    println(i)
    }
    //使用until函数,创建一个不包括其结束元素的区间
    for (i in 1 until 10) {   // i in [1, 10) 排除了 10
    println(i)
    }


     */

    //将函数用作一个函数的返回值的高阶函数。
    fun <T> lock(lock: Lock, body: () -> T): T {
        lock.lock()
        try {
            return body()
        }
        finally {
            lock.unlock()
        }
    }

    //自定义高阶函数
    private fun resultOO(num1: Int,num2: Int,results:(Int,Int) -> Int) : Int{
        return results(num1,num2)
    }

    fun main(args: Array<String>) {
        // 调用
        test()  // 结果为：无参数
        // 调用
        test(3,5) // 结果为：8
        // 调用
        test(10,sum(3,5)) // 结果为：18
    }
    //////////高阶函数/////////////////////////
    val result1 = resultOO(1,3){
        num1,num2 -> num1 + num2
    }
}