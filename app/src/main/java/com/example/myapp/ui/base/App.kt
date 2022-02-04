package com.example.myapp.ui.base

import android.app.Application
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}