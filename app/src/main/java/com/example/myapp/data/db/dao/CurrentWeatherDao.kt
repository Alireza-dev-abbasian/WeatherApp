package com.example.myapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapp.data.model.currentweather.CurrentWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao : FactoryDao<CurrentWeather>  {

    @Query("select * from CurrentWeather")
    fun getCurrentWeather(): Flow<CurrentWeather>

}