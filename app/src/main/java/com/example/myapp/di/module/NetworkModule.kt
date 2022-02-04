package com.example.myapp.di.module

import com.example.myapp.data.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String = "https://api.openweathermap.org/data/2.5/"

    @Singleton
    @Provides
    @Named("Default")
    fun provideGsonBuilder(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideConverterFactory(@Named("Default") gson: Gson): Converter.Factory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("BaseUrl") baseUrl: String,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    fun provideAppApi(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

}