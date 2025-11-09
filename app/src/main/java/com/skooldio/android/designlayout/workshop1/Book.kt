package com.skooldio.android.designlayout.workshop1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val title: String,
    val price: String,
    val imageUrl: Int
) : Parcelable