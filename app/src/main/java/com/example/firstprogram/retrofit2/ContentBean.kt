package com.example.firstprogram.retrofit2

data class ContentBean (
    val pic_s210: String,
    val bg_pic: String,
    val color: String,
    val pic_s444: String,
    val count: Int,
    val type: Int,
    val content : List<ContentT>
)

data class ContentT(
    val all_rate: String,
    val song_id: String,
    val rank_change: String,
    val author: String,
    val album_title: String
)