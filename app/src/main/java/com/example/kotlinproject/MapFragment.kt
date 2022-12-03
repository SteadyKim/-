package com.example.kotlinproject


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.provider.Settings

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult

import androidx.core.app.ActivityCompat
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.kotlinproject.databinding.FragmentMapBinding
import com.example.kotlinproject.restaurant.RestaurantData
import com.google.android.gms.common.api.ApiException
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
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI
import kotlin.math.log

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {



    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLatLng: LatLng
    private var binding: FragmentMapBinding? = null
    private val permissions = Array(2){
        android.Manifest.permission.ACCESS_COARSE_LOCATION
        ACCESS_FINE_LOCATION
    }
    val LINK="https://maps.googleapis.com/maps/api/place/photo"+
    "?maxwidth=200&photo_reference=AW30NDzirzB8gSQcN8C-XGJPPf3pEjPa025o04sLdB8Se-6BtyM6qiGtsOivqnGFZQWCQTezvPFAVkBl36noq8tX5wZBZY2SM2Zxh4o1c1iI4WS-90vyaYUgTH450lq9C8inruL00zSiiVW9Lc_AbuXn1PDmbY45Qzs_U84N5CZ-CBcLE5b4"+
    "&key=AIzaSyDNfNqFjQEOWNmDG4j7xJfTuU99-zcgc4s"

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        showDefaultLocation()
        setLocationRequestSettings()
        locationUpdate()
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
        // Define a Place ID.

        binding?.cardView?.visibility =View.GONE
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
//        requestPermissions(permissions,0)
        val requestLocation = registerForActivityResult(ActivityResultContracts.RequestPermission(),
        ACCESS_FINE_LOCATION){ isGranted ->
            if(isGranted) {
                startProcess()
            }
            else if (
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    ACCESS_FINE_LOCATION
                )
            ) {
                Toast.makeText(requireActivity(), "주변 음식점 검색을 위해 위치 권한이 허용 되어야 합니다.", Toast.LENGTH_SHORT).show()
            }
            //요청을 2회 이상 거절하면
            else noticeCantWork()
        }
        requestLocation.launch()
    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {//requestCode가 0일때
//            0 -> {
//                if (grantResults.isNotEmpty()) {//요청을 허락했을 때 정보를 갖는다
//                    var allGranted = true
//                    //요청한 권한 허용/거부 상태를 한번에 체크한다
//                    for (grant in grantResults) {
//                        if (grant != PackageManager.PERMISSION_GRANTED) {
//                            allGranted = false
//                            break
//                        }
//                    }
//                    //요청한 권한을 모두 허용했다면
//                    if (allGranted) {
//                        Log.d(TAG,"start실행")
//                        startProcess()
//                    }
//                    //요청을 1회 거절하면
//                    else if (ActivityCompat.shouldShowRequestPermissionRationale(
//                            requireActivity(),
//                            android.Manifest.permission.ACCESS_COARSE_LOCATION
//                        ) &&
//                        ActivityCompat.shouldShowRequestPermissionRationale(
//                            requireActivity(),
//                            android.Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                    ) {
//                        Toast.makeText(requireActivity(), "주변 음식점 검색을 위해 위치 권한이 허용 되어야 합니다.", Toast.LENGTH_SHORT).show()
//                        getPermission()
//                    }
//                    //요청을 2회 이상 거절하면
//                    else noticeCantWork()
//                }
//            }
//        }
//    }

    private fun noticeCantWork(){
        Toast.makeText(requireActivity(), "설정에서 위치 사용 권한을 허용해 주세요", Toast.LENGTH_SHORT).show()
        val snackBar = binding?.root?.let {
            Snackbar.make(it,"음식점 검색을 위한 위치 정보 접근 권한이 필요합니다",
                Snackbar.LENGTH_INDEFINITE
            )
        }
        snackBar?.setAction("승인") {gotoSettings()}
        snackBar?.show()
    }

    fun gotoSettings(){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

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
                locationResult.run{
                    currentLatLng=LatLng(this.locations[0].latitude,this.locations[0].longitude)
                    showCurrentLocation(this.locations[0])
                    getRestaurant(this.locations[0])
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
//        setFragmentResultListener("Food"){ key, bundle ->
//            keyword=bundle.getString(key)
//        }
        keyword = arguments?.getString("Food")
        Log.d(TAG,"$keyword 성공")
        RetrofitObject.getApiService().getRestaurant(getURL(location, keyword))
            .enqueue(object : Callback<RestaurantData> {
                override fun onResponse(
                    call: Call<RestaurantData>,
                    response: Response<RestaurantData>
                ) {
                    showRestaurant(response.body() as RestaurantData)
                }

                override fun onFailure(call: Call<RestaurantData>, t: Throwable) {
                    Toast.makeText(requireActivity(), "api request 실패.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showRestaurant(data: RestaurantData) {
        Log.d(TAG,"결과 ${data.results.size}개 성공")
        for (res in data.results){
            val position = LatLng(res.geometry.location.lat, res.geometry.location.lng)
            val markerOptions = MarkerOptions().position(position)
            val marker = mMap.addMarker(markerOptions)
            val openNow = if(res.opening_hours==null) null else res.opening_hours.open_now
            val photoReference = if(res.photos.isNullOrEmpty()) null else res.photos[0].photo_reference
            marker?.tag = "${res.name}/${res.rating}/${res.user_ratings_total}/${res.formatted_address}/${openNow}/${photoReference}"
        }
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        if(marker.position == currentLatLng){
            marker.title="현재 위치"
            marker.snippet="현재 계신 위치 입니다"
            marker.showInfoWindow()
            return true
        }
        //Glide.with(this).load(LINK).into(binding!!.imageView4)
        binding?.cardView?.visibility = View.VISIBLE
        val arr = marker.tag.toString().split("/")
        binding?.name?.text = arr[0]
        binding?.rating?.text=arr[1]
        binding?.ratingBar?.rating = arr[1].toFloat()
        val review = arr[2]+"개의 리뷰"
        binding?.ratingTotal?.text=review
        binding?.address?.text =arr[3]
        binding?.openNow?.text = when(arr[4]) {
            "true" -> "현재 영업 중"
            "false" ->"영업 중이지 않음"
            else -> "영업 중인지 알 수 없음"
        }
        Log.d(TAG, "${arr[5]} 성공")
        Glide.with(this).load(getURL(arr[5])).into(binding!!.imageView4)

    return true
    }

    override fun onMapClick(latLng: LatLng) {
        binding?.cardView?.visibility=View.GONE
    }

    //동적 URL 생성
    private fun getURL(location: Location, keyword: String?): String {
        val url = "maps/api/place/textsearch/json?query=${keyword}" +
                "&location=${location.latitude},${location.longitude}" +
                "&radius=5000&key=${API_KEY}&type=restaurant&language=ko"
        return url
    }
    private fun getURL(photoReference: String): String{
        val url = "${BASE_URL}maps/api/place/photo?maxwidth=200&" +
                "photo_reference=$photoReference&key=${API_KEY}"
    return url
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }


}