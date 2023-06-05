package com.capstone.kuma.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TipsItem(
    val img: Int,
    val title: String,
    val desc: String
) : Parcelable
