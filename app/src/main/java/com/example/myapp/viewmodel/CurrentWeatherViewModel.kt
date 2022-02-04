package com.example.myapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapp.data.enums.ErrorType
import com.example.myapp.data.model.currentweather.CurrentWeather
import com.example.myapp.repository.CurrentWeatherRepository
import com.example.myapp.util.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    app: Application,
    var repository: CurrentWeatherRepository
) : BaseViewModel(app) {

    val currentWeatherState:MutableStateFlow<Resource<CurrentWeather>> = MutableStateFlow(Resource.Loading())

    init {
        getCurrentWeather("50","130")
    }

    fun getCurrentWeather(lat:String, lon:String) = viewModelScope.launch {

        currentWeatherState.value = Resource.Loading()

        repository.getCurrentWeather(lat,lon)
            .catch {
                currentWeatherState.value = Resource.Error(it.message.toString(),null,ErrorType.SERVER_ERROR)
            }
            .collect {
                currentWeatherState.value = Resource.Success(it.data)
            }
    }

}