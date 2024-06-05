package com.example.plant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListGuidance(
    val title: String,
    val time : String,
    val photo : Int
): Parcelable
