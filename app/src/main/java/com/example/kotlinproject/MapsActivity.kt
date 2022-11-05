package com.example.kotlinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(37.541, 127.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Seoul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f))
    }

}