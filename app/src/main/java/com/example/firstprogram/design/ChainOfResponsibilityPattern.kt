package com.example.firstprogram.design

//组装责任链
class ChainOfResponsibilityPattern {

    fun main(args: Array<String>) {
        //组装责任链
        val handler1: HandlerTest = ConcreteHandler1()
        val handler2: HandlerTest = ConcreteHandler2()
        handler1.setNext(handler2)
        //提交请求
        handler1.handleRequest("two")

    }

}