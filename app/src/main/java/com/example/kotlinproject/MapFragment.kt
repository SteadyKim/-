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
import com.bumptech.glide.Glide
import com.example.kotlinproject.databinding.FragmentMapBinding
import com.example.kotlinproject.restaurant.API_KEY
import com.example.kotlinproject.restaurant.BASE_URL
import com.example.kotlinproject.restaurant.RestaurantData
import com.example.kotlinproject.restaurant.RetrofitObject
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLatLng: LatLng
    private val permissions = Array(2){
        android.Manifest.permission.ACCESS_COARSE_LOCATION
        ACCESS_FINE_LOCATION
    }
    private val callback = OnMapReadyCallback { googleMap ->
        //googleMap 객체 생성후 defaultLocation 보여준 뒤 setting 하고 현재 위치정보 업데이트
        mMap = googleMap
        showDefaultLocation()
        setLocationRequestSettings()
        locationUpdate()
    }
    private var binding: FragmentMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding inflate
        binding = FragmentMapBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //시작할때 카드뷰 숨기기
        binding?.CardView?.visibility = View.GONE
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        //권한 허용시 startProcess 아니라면 getPermission
        if(isPermitted()) {
            startProcess()
        }
        else getPermission()
    }

    //권한 허용되어 있는지 체크 허용됐으면 true, 안됐으면 false 반환
    private fun isPermitted() : Boolean {
        for (perm in permissions) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), perm) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    //프로세스 시작 mapFragment 생성후 OnMapReadyCallBack 호출
    private fun startProcess(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    //권한 허용 받는 함수
    private fun getPermission(){
        val requestLocation = registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ACCESS_FINE_LOCATION){ isGranted ->
            if(isGranted) {
                //권한허용시 startProcess
                startProcess()
            }
            else if (
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    ACCESS_FINE_LOCATION
                )
            ) {
                //요청 1회 거절시 알림
                Toast.makeText(requireActivity(), "주변 음식점 검색을 위해 위치 권한이 허용 되어야 합니다.", Toast.LENGTH_SHORT).show()
            }
            //요청 2회 이상 거절시 noticeCantWork 호출
            else noticeCantWork()
        }
        requestLocation.launch()
    }

    //권한 2회 이상 거절시 동작 할 수 없음 알리고 설정창으로 이동하게 끔 함
    private fun noticeCantWork(){
        Toast.makeText(requireActivity(), "설정에서 위치 사용 권한을 허용해 주세요", Toast.LENGTH_SHORT).show()
        val snackBar = binding?.root?.let {
            Snackbar.make(it,"음식점 검색을 위한 위치 정보 접근 권한이 필요합니다",
                Snackbar.LENGTH_INDEFINITE
            )
        }
        snackBar?.setAction("승인"){goSettings()}
        snackBar?.show()
    }

    //snackBar 클릭시 설정으로 가는 이벤트
    fun goSettings(){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", activity?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    //기본 위치를 보여줌
    private fun showDefaultLocation(){
        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(37.56,126.97))
        markerOptions.title("위치정보 가져올 수 없음")
        markerOptions.snippet("위치 권한과 GPS 활성 여부를 확인하세요")
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.56,126.97),15.0f))
    }

    //locationRequest setting하고 gps 사용 여부 확인
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

    //locationCallback 설정 업데이트, 현재 위치 정보 받아오고 이하 메서드 실행
    private fun locationUpdate(){
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.run{
                    currentLatLng=LatLng(locations[0].latitude, locations[0].longitude)
                    showCurrentLocation(locations[0])
                    getRestaurant(locations[0])
                }
            }
        }
        //권한 허가된 경우에만 requestLocaionUpdate 호출
        @SuppressLint("MissingPermission")
        if(isPermitted()) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }
    }

    //구글맵에 현재 위치를 마커로 표시
    private fun showCurrentLocation(location: Location){
        val currentLocation = LatLng(location.latitude, location.longitude)
        val markerOption = MarkerOptions().position(currentLocation).title("현재 위치입니다")
        val cameraOption= CameraPosition.Builder().target(currentLocation).zoom(15.0f).build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.clear()
        mMap.addMarker(markerOption)
        mMap.moveCamera(camera)
    }

    //resultFragment로 부터 keyword(음식명)을 받아 retrofit으로 api 통신하여 성공시 showRestaurant
    private fun getRestaurant(location: Location) {
        val keyword = arguments?.getString("Food")
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

    //지도에 주변 식당 표시
    private fun showRestaurant(data: RestaurantData) {
        for (res in data.results){
            val position = LatLng(res.geometry.location.lat, res.geometry.location.lng)
            val markerOptions = MarkerOptions().position(position).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            val marker = mMap.addMarker(markerOptions)
            val openNow = if(res.opening_hours==null) null else res.opening_hours.open_now
            val photoReference = if(res.photos.isNullOrEmpty()) null else res.photos[0].photo_reference
            marker?.tag = "${res.name}/${res.rating}/${res.user_ratings_total}/${res.formatted_address}/${openNow}/${photoReference}"
        }
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    //마커 클릭시 이벤트 card view(식당정보) 표시
    override fun onMarkerClick(marker: Marker): Boolean {
        if(marker.position == currentLatLng){
            marker.title="현재 위치"
            marker.snippet="현재 계신 위치 입니다"
            marker.showInfoWindow()
            return true
        }
        binding?.CardView?.visibility = View.VISIBLE
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
        Glide.with(this).load(getURL(arr[5])).into(binding!!.imgRes)
        binding?.btnFav?.setOnClickListener {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.maker))
        }
        return true
    }

    //지도 클릭시 이벤트 (cardView 감추기)
    override fun onMapClick(latLng: LatLng) {
        binding?.CardView?.visibility=View.GONE
    }

    //동적 URL 생성(textsearch api 호출용)
    private fun getURL(location: Location, keyword: String?): String {
        val url = "maps/api/place/textsearch/json?query=${keyword}" +
                "&location=${location.latitude},${location.longitude}" +
                "&radius=5000&key=$API_KEY&type=restaurant&language=ko"
        return url
    }

    //동적 URL 생성(photo api 호출용)
    private fun getURL(photoReference: String): String{
        val url = "${BASE_URL}maps/api/place/photo?maxwidth=200&" +
                "photo_reference=$photoReference&key=$API_KEY"
        return url
    }

    //fragment 삭제시 binding=null 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

}