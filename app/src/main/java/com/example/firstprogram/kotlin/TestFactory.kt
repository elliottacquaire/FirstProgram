package com.example.firstprogram.kotlin

interface TestFactory {
    fun newThread(r: Runnable): Thread
}