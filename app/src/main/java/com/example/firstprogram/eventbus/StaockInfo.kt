package com.example.firstprogram.eventbus

import java.lang.reflect.Method

data class StaockInfo(
    val objec : Any?,
    val methodName : String?,
    val method : Method,
    val isMain : Boolean,
    val isStick : Boolean
)
