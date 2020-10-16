package com.example.firstprogram.ipc

import androidx.core.util.Pools.SynchronizedPool


//对象池 实现
class PoolDateBean {
    var id: Int? = 0

    var name: String? = ""

    fun recycle() {
        sPool.release(this)
    }

    companion object{

        private val sPool = SynchronizedPool<PoolDateBean>(1000)

        @JvmStatic
        fun obtain(): PoolDateBean? {
            val instance = sPool.acquire()
            return instance ?: PoolDateBean()
        }
    }


}