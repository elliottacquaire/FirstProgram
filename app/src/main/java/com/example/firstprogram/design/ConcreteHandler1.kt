package com.example.firstprogram.design

import com.orhanobut.logger.Logger

//具体处理者角色1
class ConcreteHandler1 : HandlerTest() {
    override fun handleRequest(request: String?) {

        if (request.equals("one")) {
            Logger.d("具体处理者1负责处理该请求！")
        } else {
            if (getNext() != null) {
                getNext()?.handleRequest(request)
            } else {
                Logger.d("没有人处理该请求！")
            }
        }

    }
}