package com.example.myapp.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.data.model.currentweather.Main
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherMainConverter {

    @TypeConverter
    fun toDataBase(data: Main): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromDataBase(data: String): Main {
        val type = object : TypeToken<Main>() {}.type
        return Gson().fromJson(data, type)
    }
}