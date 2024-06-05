package com.example.plant.ui.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val username: String,
    val date: String,
    val comment : String
): Parcelable


