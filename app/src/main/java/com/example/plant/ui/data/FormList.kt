package com.example.plant.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class FormList(
    val name: String,
    val date: String,
    val title : String
): Parcelable

