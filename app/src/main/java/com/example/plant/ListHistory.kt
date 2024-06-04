package com.example.plant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListHistory (
    val name: String,
    val description:String,
    val percentage:String,
    val time : String,
    val photo : Int
):Parcelable