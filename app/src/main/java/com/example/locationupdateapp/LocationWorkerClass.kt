package com.example.locationupdateapp

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.locationupdateapp.repository.LocationRepository
import java.lang.Exception


class LocationWorkerClass
@WorkerInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val locationRepository: LocationRepository
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {

        return try {
            locationRepository.getLocation()
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}
