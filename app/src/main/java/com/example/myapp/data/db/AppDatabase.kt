package com.example.myapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapp.data.db.converter.*
import com.example.myapp.data.db.dao.CurrentWeatherDao
import com.example.myapp.data.model.currentweather.CurrentWeather

@Database(
    entities = [CurrentWeather::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    CurrentWeatherCloudsConverter::class, CurrentWeatherCoordConverter::class,
    CurrentWeatherMainConverter::class, CurrentWeatherSysConverter::class,
    CurrentWeatherWindConverter::class, CurrentWeatherConverter::class
)

abstract class AppDatabase : RoomDatabase() {

    abstract val currentWeatherDao: CurrentWeatherDao

}

