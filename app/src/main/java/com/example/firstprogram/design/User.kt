package com.example.firstprogram.design

import android.util.Log

class User : IUserDao {
    private val TAG = "aaa"
    override fun save(): Int {
        Log.i(TAG,"----save---")
        return 10
    }

    override fun delete(name: String) {
        Log.i(TAG,"-delete---$name")
    }

    //kotlin 数组
    private fun arrayWrite(){
        var fixSize = arrayOfNulls<Int>(10)
        val arr1 = arrayOf(1, 2, 3)
        var szie = arrayOf(6)
        val empty = emptyArray<Int>()

        val intArra: IntArray = intArrayOf(1,2,3)
        val longArr: LongArray = longArrayOf(1L,2L,3L)
        val floatArr: FloatArray = floatArrayOf(1.0f,2.0f,3.0f)
        val doubleArr: DoubleArray = doubleArrayOf(1.0,2.02,3.03333)
        val booleanArr: BooleanArray = booleanArrayOf(false,true,false)

        val intArr = intArrayOf(1,2,3)
        for(item in intArr){
            println(item)//遍历intArr里面的元素，item就是元素本身
        }
        for (index in intArr.indices){
            println(intArr[index])//遍历initArr索引的元素，从0开始
            println(intArr.get(index))//可以通过get(索引)来获取元素
        }
        for (index in intArr.indices){
            intArr[index] = 0 //与java一样，可以这样修改数据
            intArr.set(index,1) //kotlin可以通过set函数进行修改数据
        }

        //基本类型的二维数组
        val arr = Array(3){IntArray(3)}//三个长度为3的Int数组的二维数组
        print(arr[1][1])
        for (one in arr){
            println()
            for (two in one){
                print(two)
            }
        }


    }
}