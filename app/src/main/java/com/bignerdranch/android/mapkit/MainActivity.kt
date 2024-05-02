package com.bignerdranch.android.mapkit

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {
    lateinit var mapView: MapView
    lateinit var locationButton: FloatingActionButton
    lateinit var trafficButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("d25d1d33-95ae-43a5-af52-752f0524870d")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        locationButton = findViewById(R.id.floatingActionButton)
        trafficButton = findViewById(R.id.toggleButton)
        mapView = findViewById(R.id.mapView)

        locationButton.setOnClickListener() {
            toLocation()
        }

        var mapKit = MapKitFactory.getInstance()
        var trafficLayer = mapKit.createTrafficLayer(mapView.mapWindow)

        locationPermission()

        trafficButton.setOnCheckedChangeListener { buttonView, isChecked ->
            trafficLayer.isTrafficVisible = isChecked
        }
    }

    override fun onStop() {
        super.onStop()

        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()

        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    fun locationPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
                0
            )
        }
    }

    fun toLocation() {
        mapView.map.move(
            CameraPosition(
                Point(55.354880, 86.086396),
                18f,
                0f,
                0f
            ),
            Animation(Animation.Type.SMOOTH, 5f),
            null
        )
    }
}