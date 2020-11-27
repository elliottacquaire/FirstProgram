package com.example.firstprogram.retrofit2

/**
 * {"resultcode":"200","reason":"Return Successd!","result":{"province":"安徽","city":"合肥","areacode":"0551","zip":"230000","company":"移动","card":""},"error_code":0}
 */
data class Bean(
    val error_code: Int,
    val reason: String,
    val result: ResultTT,
    val resultcode: String
)

data class ResultTT(
    val areacode: String,
    val card: String,
    val city: String,
    val company: String,
    val province: String,
    val zip: String
)