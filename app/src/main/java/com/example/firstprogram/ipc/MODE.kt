package com.example.firstprogram.ipc

import androidx.annotation.IntDef
import com.example.firstprogram.ipc.MODE.Companion.MODE_LIST
import com.example.firstprogram.ipc.MODE.Companion.MODE_STANDARD
import com.example.firstprogram.ipc.MODE.Companion.MODE_TABS

//1.指定注解的保留策略，AnnotationRetention.SOURCE表示只保留源码中，编译时删除。还有CLASS和RUNTIME
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
//2.定义int 值 ，
@IntDef(flag = true, value = [MODE_STANDARD, MODE_LIST, MODE_TABS])
annotation class MODE {
    companion object {
        const val MODE_STANDARD = 1
        const val MODE_LIST = 2
        const val MODE_TABS = 4
    }
}