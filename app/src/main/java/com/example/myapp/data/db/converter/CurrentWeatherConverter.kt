package com.example.myapp.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.data.model.currentweather.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherConverter {

    @TypeConverter
    fun toDataBase(data: List<Weather>): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromDataBase(data: String): List<Weather> {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(data, type)
    }
}