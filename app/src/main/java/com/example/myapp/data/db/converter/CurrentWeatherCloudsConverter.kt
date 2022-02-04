package com.example.myapp.data.db.converter

import androidx.room.TypeConverter
import com.example.myapp.data.model.currentweather.Clouds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentWeatherCloudsConverter {

    @TypeConverter
    fun toDataBase(data: Clouds): String {
        return Gson().toJson(data)
    }

    @TypeConverter
    fun fromDataBase(data: String): Clouds {
        val type = object : TypeToken<Clouds>() {}.type
        return Gson().fromJson(data, type)
    }
}