package com.example.kotlinproject


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.fragment.app.setFragmentResultListener
import com.example.kotlinproject.databinding.FragmentMapBinding
import com.example.kotlinproject.restaurant.RestaurantData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        showDefaultLocation()
        setLocationRequestSettings()
        locationUpdate()
    }

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    var binding: FragmentMapBinding? = null
    val permissions = Array(2){
        android.Manifest.permission.ACCESS_COARSE_LOCATION
        android.Manifest.permission.ACCESS_FINE_LOCATION
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if(isPermitted()) startProcess()
        else getPermission()
    }

    private fun isPermitted() : Boolean {
        for (perm in permissions) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), perm) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    private fun startProcess(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    private fun getPermission(){
//        val contract = ActivityResultContracts.RequestPermission()
//        val activityResultLauncher = registerForActivityResult(contract){
//            isGranted ->
//            if
//        }
        //deprecated code, register..로 추후 변경
        requestPermissions(permissions,0)
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
                            break
                        }
                    }
                    //요청한 권한을 모두 허용했다면
                    if (allGranted) {
                        Log.d(TAG,"start실행")
                        startProcess()
                    }
                    //요청을 1회 거절하면
                    else if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        Toast.makeText(requireActivity(), "주변 음식점 검색을 위해 위치 권한이 허용 되어야 합니다.", Toast.LENGTH_SHORT).show()
                        getPermission()
                    }
                    //요청을 2회 이상 거절하면
                    else noticeCantWork()
                }
            }
        }
    }

    private fun noticeCantWork(){
        Toast.makeText(requireActivity(), "설정에서 위치 사용 권한을 허용해 주세요", Toast.LENGTH_SHORT).show()
//        val snackBar = Snackbar.make(
//            binding.root,
//            "음식점 검색을 위한 위치 정보 접근 권한이 필요합니다",
//            Snackbar.LENGTH_INDEFINITE
//        )
//        snackBar.setAction("승인") {}
//        snackBar.show()
    }

//    fun gotoSettings(){
//        val intent = Intent()
//        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//        val uri = Uri.fromParts("package")
//        intent.data = uri
//        startActivity(intent)
//    }

    private fun showDefaultLocation(){
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(37.56,126.97))
        markerOptions.title("위치정보 가져올 수 없음")
        markerOptions.snippet("위치 권한과 GPS 활성 여부를 확인하세요")
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.56,126.97),15.0f))
    }


    private fun setLocationRequestSettings() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        //GPS가 켜져있는 경우 아무 것도 안함
        task.addOnFailureListener { exception ->
            // GPS가 꺼져있을 경우
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(
                        requireActivity(),
                        0
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    private fun locationUpdate(){
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{
                    for (location in it.locations){
                        showCurrentLocation(location)
                        getRestaurant(location)
                    }
                }
            }
        }
        @SuppressLint("MissingPermission")
        if(isPermitted()) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
    }

    private fun showCurrentLocation(location: Location){
        val currentLocation = LatLng(location.latitude, location.longitude)
        val markerOption = MarkerOptions().position(currentLocation).title("현재 위치입니다")
        val cameraOption= CameraPosition.Builder().target(currentLocation).zoom(15.0f).build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.clear()
        mMap.addMarker(markerOption)
        mMap.moveCamera(camera)
    }

    private fun getRestaurant(location: Location) {

        var keyword:String? = null
        setFragmentResultListener("Food"){ key, bundle ->
            keyword=bundle.getString(key)
        }
        Log.d(TAG,"$keyword 받음")
        RetrofitObject.getApiService().getRestaurant(getURL(location, keyword))
            .enqueue(object : Callback<RestaurantData> {
                override fun onResponse(
                    call: Call<RestaurantData>,
                    response: Response<RestaurantData>
                ) {
                    showRestaurant(response.body() as RestaurantData)
                }

                override fun onFailure(call: Call<RestaurantData>, t: Throwable) {
                    Toast.makeText(requireActivity(), "주변에 식당이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showRestaurant(data: RestaurantData) {
        for (res in data.results){
            val position = LatLng(res.geometry.location.lat, res.geometry.location.lng)
            val snippet = res.formatted_address
            val marker = MarkerOptions().position(position).snippet(snippet).title(res.name)
            mMap.addMarker(marker)
        }
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    //동적 URL 생성
    private fun getURL(location: Location, keyword: String?): String {
        val url = "maps/api/place/textsearch/json?location=" +
                "${location.latitude},${location.longitude}" +
                "&query=${keyword}&key=${API_KEY}&radius=500&type=restaurant"
        return url
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}