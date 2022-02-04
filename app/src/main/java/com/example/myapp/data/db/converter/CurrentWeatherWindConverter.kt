package com.example.myapp.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.data.model.currentweather.Wind
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherWindConverter {

    @TypeConverter
    fun toDataBase(data: Wind): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromDataBase(data: String): Wind {
        val type = object : TypeToken<Wind>() {}.type
        return Gson().fromJson(data, type)
    }
}