package com.example.kotlinproject.activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.kotlinproject.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    val permissions = Array<String>(2){
        android.Manifest.permission.ACCESS_COARSE_LOCATION
        android.Manifest.permission.ACCESS_FINE_LOCATION
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    var currentMarker:Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stringExtra = intent.getStringExtra("food")
//        println("stringExtra = ${stringExtra}")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if(isPermitted()) startProcess()
        else getPermission()
    }

    fun isPermitted() : Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PERMISSION_GRANTED)
                return false
        }
        return true
    }

    fun startProcess(){
        Log.d(TAG,"Start 실행")
        val mapFragment = supportFragmentManager.findFragmentById(com.example.kotlinproject.R.id.map)
                as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showDefaultLocation()
        setLocationRequestSettings()
        locationUpdate()
        Log.d(TAG,"다 실행")
    }

    fun locationUpdate(){
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{
                    for ((i, location) in it.locations.withIndex()){
                        showCurrentLocation(location)
                    }
                }
            }
        }
        @SuppressLint("MissingPermission")
        if(isPermitted()) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
    }

    fun showCurrentLocation(location: Location){
        val currentLocation = LatLng(location.latitude, location.longitude)
        val markerOption = MarkerOptions().position(currentLocation).title("현재 위치입니다")
        val cameraOption= CameraPosition.Builder().target(currentLocation).zoom(15.0f).build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.clear()
        mMap.addMarker(markerOption)
        mMap.moveCamera(camera)
    }

    fun showDefaultLocation(){
        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 권한과 GPS 활성 여부를 확인하세요"
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(37.56,126.97))
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)
        currentMarker = mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.56,126.97),15.0f))
    }

    fun setLocationRequestSettings() {
        locationRequest = LocationRequest.create().apply() {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        //GPS가 켜져있는 경우 아무 것도 안함
        task.addOnFailureListener { exception ->
            // GPS가 꺼져있을 경우
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(
                        this,
                        0
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    fun getPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {//requestCode가 0일때
            0 -> {
                if (grantResults.isNotEmpty()) {//요청을 허락했을 때 정보를 갖는다
                    var allGranted = true
                    //요청한 권한 허용/거부 상태를 한번에 체크한다
                    for (grant in grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) {
                            allGranted = false
                            break;
                        }
                    }
                    //요청한 권한을 모두 허용했다면
                    if (allGranted) {
                        startProcess()
                    }
                    //요청을 1회 거절하면
                    else if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        Toast.makeText(this, "주변 음식점 검색을 위해 위치 권한이 허용 되어야 합니다.", Toast.LENGTH_SHORT).show()
                        getPermission()
                    }
                    //요청을 2회 이상 거절하면
                    else noticeCantWork()
                }
            }
        }
    }

    fun noticeCantWork(){
        Toast.makeText(this, "설정에서 위치 사용 권한을 허용해 주세요", Toast.LENGTH_SHORT).show()
        val snackBar = Snackbar.make(
            binding.root,
            "음식점 검색을 위한 위치 정보 접근 권한이 필요합니다",
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction("승인") { gotoSettings() }
        snackBar.show()
    }

    fun gotoSettings(){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}