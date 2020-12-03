package com.example.firstprogram.design

import com.orhanobut.logger.Logger

class ConcreteHandler2 : HandlerTest(){

    override fun handleRequest(request: String?) {
        if (request.equals("two")) {
            Logger.d("具体处理者2负责处理该请求！")
        } else {
            if (getNext() != null) {
                getNext()?.handleRequest(request)
            } else {
                Logger.d("没有人处理该请求！")
            }
        }
    }


}