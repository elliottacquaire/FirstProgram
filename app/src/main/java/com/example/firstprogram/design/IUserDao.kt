package com.example.firstprogram.design

interface IUserDao {
    fun save() : Int
    fun delete(name : String)
}