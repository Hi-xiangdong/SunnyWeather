package com.android.getloctest.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.location.LocationManager
import android.os.Bundle
import com.android.getloctest.R
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.Toast
import android.location.LocationListener
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.getloctest.model.RealtimeResponse
import com.android.getloctest.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var tvTem: TextView
    private lateinit var tvHum: TextView
    private var locationText: TextView? = null
    private var provider: String? = null
    private var locationManager: LocationManager? = null

    private val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationText = findViewById(R.id.tv_location)
        tvTem = findViewById(R.id.tv_tem)
        tvHum = findViewById(R.id.tv_hum)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val permissionCheck = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("权限log", "没有权限")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Log.i("权限log", "拒绝声明")
                Toast.makeText(this, "u had rejected the request", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )
            }
        } else {
            Log.i("权限log", "有权限")
        }
        val providerList = locationManager!!.getProviders(true)
        var location: Location? = null
        for (pro in providerList) {
            Log.i(localClassName, "pro $pro")
            val l = locationManager!!.getLastKnownLocation(pro) ?: continue
            if (location == null || l.accuracy < location.accuracy) {
                location = l
                provider = pro
            }
        }
        Log.i(localClassName, provider!!)
        location?.let { showLocation(it) }

        locationManager!!.requestLocationUpdates(
            provider!!,
            (10 * 1000).toLong(),
            1f,
            locationListener
        )

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                tvTem.text = weather.temperature.toString()
                tvHum.text = weather.humidity.toString()
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        if (location != null) {
            viewModel.refreshWeather(location.longitude.toString(), location.latitude.toString())
        }
    }

    private fun showLocation(location: Location) {
        val curPos = "经度：" + location.latitude + " 纬度：" + location.longitude
        locationText!!.text = curPos
    }

    var locationListener = LocationListener { location -> showLocation(location) }

    override fun onDestroy() {
        super.onDestroy()
        if (locationManager != null) {
            locationManager!!.removeUpdates(locationListener)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val location = locationManager!!.getLastKnownLocation(
                    provider!!
                )
                location?.let { showLocation(it) } ?: Log.i(localClassName, "location is null")
            } else {
                Toast.makeText(this, "deny", Toast.LENGTH_SHORT).show()
            }
        }
    }
}