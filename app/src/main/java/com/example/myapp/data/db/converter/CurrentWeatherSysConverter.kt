package com.example.myapp.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.data.model.currentweather.Sys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherSysConverter {

    @TypeConverter
    fun toDataBase(data: Sys): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromDataBase(data: String): Sys {
        val type = object : TypeToken<Sys>() {}.type
        return Gson().fromJson(data, type)
    }
}