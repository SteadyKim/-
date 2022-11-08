package com.example.kotlinproject

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.kotlinproject.databinding.ActivitySelectBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar

val permission = Array<String>(2){
    android.Manifest.permission.ACCESS_COARSE_LOCATION
    android.Manifest.permission.ACCESS_FINE_LOCATION
}

class SelectActivity : AppCompatActivity() {

    lateinit var binding:ActivitySelectBinding
    //2. 레이아웃(root뷰) 표시
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater);

        setContentView(binding.root)


        binding.btnHistory.setOnClickListener {
            val nextIntent = Intent(this@SelectActivity, HistoryActivity ::class.java)
            startActivity(nextIntent)
        }

        binding.btnStartRandom.setOnClickListener {
            val nextIntent = Intent(this@SelectActivity, ResultActivity ::class.java)
            intent.putExtra("result",R.drawable.select_bibim_bap)    //음식 이름 넣어두기 비빔밥말고 다른거도 가능하도록...
            startActivity(nextIntent)
        }

        binding.btnMap2.setOnClickListener {
            getPermission()
        }

        binding.btnAnything.setOnClickListener {
            val intent = Intent(this@SelectActivity, ResultActivity::class.java )
            startActivity(intent)
        }

    }

    //위치 권한을 받는 메서드
    fun getPermission(){
        ActivityCompat.requestPermissions(this, permission ,0)
    }

    //권한요청 처리결과 수신
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
                        val intent = Intent(this, MapsActivity::class.java)
                        startActivity(intent)
                        setLocation()
                    }//안했다면
                    else if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) &&
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) Toast.makeText(this, "권한이 없어 해당 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    else cantWork()

                }
            }
        }
    }

    fun setLocation() {

        val locationRequest = LocationRequest.create().apply() {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnFailureListener { exception ->
            // GPS가 꺼져있을 경우
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(
                        this@SelectActivity,
                        1
                    )

                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    fun gotoPermissionSettings(){//설정화면으로 이동
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    fun cantWork(){//위치 정보 사용할수 없음을 알림
        Toast.makeText(this, "권한이 없어 해당 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
        val snackBar = Snackbar.make(
            binding.root,
            "음식점 검색을 위한 위치 정보 접근 권한이 필요합니다",
            Snackbar.LENGTH_INDEFINITE
        )
        snackBar.setAction("승인") {gotoPermissionSettings()}
        snackBar.show()
    }

}