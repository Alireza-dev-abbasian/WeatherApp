package com.example.myapp.data.model.currentweather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Clouds(
    val all: Int
):Parcelable