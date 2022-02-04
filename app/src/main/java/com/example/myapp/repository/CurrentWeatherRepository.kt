package com.example.myapp.repository

import com.example.myapp.data.api.ApiService
import com.example.myapp.data.db.AppDatabase
import com.example.myapp.data.model.currentweather.CurrentWeather
import com.example.myapp.util.network.Resource
import com.example.myapp.util.network.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    val db: AppDatabase,
    val api: ApiService
) {

    fun getCurrentWeather(lat:String,lon:String):Flow<Resource<CurrentWeather>> = networkBoundResource(
        query = {
            db.currentWeatherDao.getCurrentWeather()
        },
        fetch = {
            api.getCurrentWeather(lat,lon)
        },
        saveFetchResult = { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    db.currentWeatherDao.insert(it)
                }
            }
        }
    )

}