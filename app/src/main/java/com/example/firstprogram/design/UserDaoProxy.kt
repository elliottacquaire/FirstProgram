package com.example.firstprogram.design

import android.util.Log

/**
 *  * 代理对象,静态代理
 */
class UserDaoProxy(proxy:IUserDao) : IUserDao {
    private val TAG = "aaa"
    //接收保存目标对象
    private var target: IUserDao? = null

    init {
        Log.i(TAG,"----init---")
        this.target = proxy
    }

    override fun save(): Int {
        Log.i(TAG,"----save--proxy-")
        return target?.save()?:0
    }

    override fun delete(name: String) {
        Log.i(TAG,"----delete--proxy-")
    }
}