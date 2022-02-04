package com.example.myapp.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.data.model.currentweather.Coord
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherCoordConverter {

    @TypeConverter
    fun toDataBase(data: Coord): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromDataBase(data: String): Coord {
        val type = object : TypeToken<Coord>() {}.type
        return Gson().fromJson(data, type)
    }
}