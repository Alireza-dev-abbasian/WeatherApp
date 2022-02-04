package com.example.myapp.data.model.currentweather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coord(
    val lat: Int,
    val lon: Int
):Parcelable