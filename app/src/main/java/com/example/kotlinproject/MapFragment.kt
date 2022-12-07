package com.example.kotlinproject


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.location.Location
import android.net.Uri
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.provider.Settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.kotlinproject.databinding.FragmentMapBinding
import com.example.kotlinproject.restaurant.API_KEY
import com.example.kotlinproject.restaurant.BASE_URL
import com.example.kotlinproject.restaurant.RestaurantData
import com.example.kotlinproject.restaurant.RetrofitObject
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

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
import com.google.type.ColorProto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var currentLatLng: LatLng
    private val callback = OnMapReadyCallback { googleMap ->
        //onMapReadyCallback시에 googleMap 객체 생성후 defaultLocation 보여준 뒤
        //setting 하고 현재 위치정보 업데이트
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
        binding?.cdvInfo?.visibility = View.GONE
        //fusedLocationClient 객체 생성
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        //위치 권한이 허용됐다면 startProcess() 아니라면 getPermission()에서 권한 요청
        if(isPermitted()) {
            startProcess()
        }
        else getPermission()
    }

    //권한 허용되어 있는지 체크하는 함수
    private fun isPermitted() : Boolean {
        if (ContextCompat.checkSelfPermission(requireActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false
        return true
    }

    //프로세스를 시작하는 함수, mapFragment 생성후 OnMapReadyCallback함
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
                //getPermission()
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
        //승인 버튼 누르면 goSettings() 실행
        snackBar?.setAction("승인"){
            goSettings()
        }
        snackBar?.show()
    }

    //snackBar 클릭시 설정으로 가는 이벤트
    private fun goSettings(){
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

    //locationRequest setting하고 GPS 사용 여부 확인
    private fun setLocationRequestSettings() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = PRIORITY_HIGH_ACCURACY
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
        //권한 허가된 경우에만 requestLocationUpdate 실행
        @SuppressLint("MissingPermission")
        if(isPermitted()) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
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

    //resultFragment로 부터 keyword(음식명)을 받아 retrofit2를 사용한 REST api 통신
    private fun getRestaurant(location: Location) {
        val keyword = arguments?.getString("Food")
        RetrofitObject.getApiService().getRestaurant(getURL(location, keyword))
            .enqueue(object : Callback<RestaurantData> {
                //api 통신 성공시 응답 결과 casting 하여 showRestaurant() 호출
                override fun onResponse(
                    call: Call<RestaurantData>,
                    response: Response<RestaurantData>
                ) {
                    showRestaurant(response.body() as RestaurantData)
                }
                //api 통신 실패시 Toast로 알림
                override fun onFailure(call: Call<RestaurantData>, t: Throwable) {
                    Toast.makeText(requireActivity(), "api request 실패.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    //지도에 주변 식당 표시
    private fun showRestaurant(data: RestaurantData) {
        //모든 result를 순회하며 마커표시
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
        //위치정보 업데이트 중단
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    //마커 클릭시 이벤트 설정 함수
    override fun onMarkerClick(marker: Marker): Boolean {
        var flag = true
        //현재 위치 마커를 눌렀을 때는 위치 알리고 함수 반환
        if (marker.position == currentLatLng) {
            marker.title = "현재 위치"
            marker.snippet = "현재 계신 위치 입니다"
            marker.showInfoWindow()
            return true
        }
        //card view(식당정보)표시
        binding?.cdvInfo?.visibility = View.VISIBLE
        val arr = marker.tag.toString().split("/")
        binding?.txtName?.text = arr[0]
        binding?.txtRating?.text = arr[1]
        binding?.ratingBar?.rating = arr[1].toFloat()
        val review = arr[2] + "개의 리뷰"
        binding?.txtTotal?.text = review
        binding?.txtAddress?.text = arr[3]
        binding?.txtOpen?.text = when (arr[4]) {
            "true" -> "현재 영업 중"
            "false" -> "영업 중이지 않음"
            else -> "영업 중인지 알 수 없음"
        }
        binding?.imvPhoto?.let { Glide.with(this).load(getURL(arr[5])).into(it) }
        //즐겨찾기 버튼 누르면 마커 색 변화
        binding?.btnFav?.setOnClickListener {
            if (flag) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                flag=false
            }
            else {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                flag=true
            }
        }
        return true
    }

    //지도 클릭시 이벤트 (card view 감추기)
    override fun onMapClick(latLng: LatLng) {
        binding?.cdvInfo?.visibility=View.GONE
    }

    //textsearch api 호출용 동적 URL 생성
    private fun getURL(location: Location, keyword: String?): String {
        return "maps/api/place/textsearch/json?query=${keyword}" +
                "&location=${location.latitude},${location.longitude}" +
                "&radius=5000&key=$API_KEY&type=restaurant&language=ko"
    }

    //Glide 라이브러리를 이용한 photo api 호출용 동적 URL 생성
    private fun getURL(photoReference: String): String{
        return "${BASE_URL}maps/api/place/photo?maxwidth=200&" +
                "photo_reference=$photoReference&key=$API_KEY"
    }

    //fragment Destroy시에 binding=null 메모리 누수 방지
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

}