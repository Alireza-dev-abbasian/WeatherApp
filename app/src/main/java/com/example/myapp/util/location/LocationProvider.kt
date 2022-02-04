package com.example.myapp.util.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.example.myapp.data.model.currentweather.Coord
import com.example.myapp.util.checkLocationPermission
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlin.math.roundToInt

class LocationProvider(val context: Context): LiveData<Coord>() {

    lateinit var locationRequest: LocationRequest
    lateinit var locationManager: LocationManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun getLocation() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 2000
            numUpdates = 2
        }
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        checkLocationPermission(
            context,
            permissionGranted = {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLatLong()
                } else {
                    turnOnGPS()
                }
            },
            permissionDenied = null
        )

    }

    fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).also {
            it.setAlwaysShow(true)
        }
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener {
            getLatLong()
        }.addOnFailureListener { e ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun getLatLong() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            callbackLocation,
            Looper.getMainLooper()
        )

    }

    private val callbackLocation = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val location = p0.lastLocation
            Log.i("locaaa", "onLocationResult: " + location.latitude + "//" + location.longitude)
            postValue(Coord(location.latitude.roundToInt(), location.longitude.roundToInt()))
        }
    }

}