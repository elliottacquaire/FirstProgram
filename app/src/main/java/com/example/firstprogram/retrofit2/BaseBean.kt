package com.example.firstprogram.retrofit2

data class BaseBean<T>(
    val code: Int,
    val message: String,
    val result: T
)

