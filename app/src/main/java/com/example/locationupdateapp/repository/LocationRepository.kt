package com.example.locationupdateapp.repository

import android.annotation.SuppressLint
import android.os.Looper
import com.example.localdatabase.Entity
import com.example.localdatabase.RoomDao
import com.example.locationupdateapp.di.impl1
import com.google.android.gms.location.*
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository
@Inject
constructor(
        @impl1 val locationRequestWhenTimeChanges: LocationRequest,
        @impl1 val locationRequestWhenDistanceChanges: LocationRequest,
        val roomDao: RoomDao,
        val fusedLocationClient: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with local database
                    runBlocking {
                        roomDao.insertItem(
                            Entity(
                                0,
                                location.latitude,
                                location.longitude,
                                SimpleDateFormat("hh:mm ss a", Locale.getDefault()).format(Date()),
                                SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date())
                            )

                        )
                    }
                }
            }
        }
        startLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationCallback: LocationCallback) {
        // we have created two request becuase both the condition i.e 200 meter and 30 sec were not getting achieved independently from one request.

        //this request will trigger when time changes to 30 sec
        fusedLocationClient.requestLocationUpdates(
            locationRequestWhenTimeChanges,
            locationCallback,
            Looper.getMainLooper()
        )

        //this request will trigger when time distance changes to 200 meter
        fusedLocationClient.requestLocationUpdates(
            locationRequestWhenDistanceChanges,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}