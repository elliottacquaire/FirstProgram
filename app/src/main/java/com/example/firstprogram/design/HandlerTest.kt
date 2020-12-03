package com.example.firstprogram.design

/**
 * 抽象处理者（Handler）角色：定义一个处理请求的接口，包含抽象处理方法和一个后继连接。
 *
 * 有多个对象可以处理一个请求，哪个对象处理该请求由运行时刻自动确定。
可动态指定一组对象处理请求，或添加新的处理者。
在不明确指定请求处理者的情况下，向多个处理者中的一个提交请求。

 */
abstract class HandlerTest {

    private var next: HandlerTest? = null
    open fun setNext(next: HandlerTest?) {
        this.next = next
    }

    open fun getNext(): HandlerTest? {
        return next
    }

    //处理请求的方法
    abstract fun handleRequest(request: String?)

}