package com.example.locationupdateapp.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.localdatabase.RoomDao
import com.example.locationupdateapp.R
import com.example.locationupdateapp.databinding.ActivityMainBinding
import com.example.locationupdateapp.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var roomDao: RoomDao
    private var activityMainBinding:ActivityMainBinding? = null
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(activityMainBinding?.root)

        checkGpsEnabledOrNot()
        observeLocationPermissionStatus()
        observeLocationFromLocaldatabase()


    }

    fun observeLocationFromLocaldatabase() {
        roomDao.getItemsList(SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date()))
            .observe(this, Observer {
                if(it.size>0) {
                    activityMainBinding?.tvNoOfTimesDataUpdated?.setText(it.size.toString())
                    activityMainBinding?.tvLastUpdatedTime?.setText(it.get(it.size - 1).dataUploadtime)
                }
            })
    }

    fun checkGpsEnabledOrNot() {
        if (mainActivityViewModel.isGPSEnabled()) {
            mainActivityViewModel.checkLocationPermission()
        } else {
            Toast.makeText(this, "Please turn on the GPS and restart the App.", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun observeLocationPermissionStatus() {
        mainActivityViewModel.LiveDataLocationPermission.observe(this, Observer {
            if (it) {
                mainActivityViewModel.startWorkManager()
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), 2000
                )
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2000 -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted.
                    mainActivityViewModel.startWorkManager()
                } else {
                    Toast.makeText(this, getString(R.string.permission_string), Toast.LENGTH_LONG)
                        .show()
                    ActivityCompat.finishAffinity(this)
                }
                return
            }
        }
    }
}