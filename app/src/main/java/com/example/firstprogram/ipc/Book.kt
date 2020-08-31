package com.example.firstprogram.ipc

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(var id : Int,val name: String, val price: Int) : Parcelable {
}