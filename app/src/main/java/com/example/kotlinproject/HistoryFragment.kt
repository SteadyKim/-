package com.example.kotlinproject

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.databinding.ActivityHistoryBinding
import com.example.kotlinproject.databinding.FragmentHistoryBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.RandomFood
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.recyclerview.RecyclerViewAdapter
import com.google.firebase.database.*
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.*


class HistoryFragment : Fragment() {
    //Room 관련 변수
    var db: AppDatabase? = null
    var foodsList = mutableListOf<Foods>()

    //fireBase 변수
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val databaseReference : DatabaseReference = database.getReference()
    lateinit var mySnapShot : DataSnapshot

    var binding: FragmentHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnKakao?.setOnClickListener {
            KakaoSdk.init(requireContext(), "a3371d68064ad62c0ef12f967df3741c")

            val defaultFeed = makeKakaoTemplate()
            //카카오톡 설치 여부 확인
            startKakaoLink(defaultFeed)
        }

        //DB 초기화
        db = AppDatabase.getInstance(requireContext())

        //이전에 저장한 내용 모두 불러와서 추가하기 - Room 사용
        val savedFoods = db!!.FoodsDao().getAll()
        if (savedFoods.isNotEmpty()) {
            foodsList.addAll(savedFoods)
        }

        /**
         * Firebase초기화
         */
        initFirebase()
        //어댑터, 아이템 클릭 : 아이템 삭제
        val adapter = RecyclerViewAdapter(foodsList)

        adapter.setItemClickListener(object : RecyclerViewAdapter.ItemClickListener {

            override fun onDeleteClick(position: Int) {
                /**
                 * 콜백 받음
                 */
                val foods = foodsList[position]
                db?.FoodsDao()?.delete(foods = foods) //DB에서 삭제
                foodsList.removeAt(position) //리스트에서 삭제
                adapter.notifyDataSetChanged() //리스트뷰 갱신
                /**
                 * Firebase 데이터 지우기
                 */
                deleteFirebaseData(foods)
            }

            private fun deleteFirebaseData(foods: Foods) {
                if (::mySnapShot.isInitialized) {
                    val foodsSnapShot = mySnapShot.child("Foods")
                    foodsSnapShot.value

                    //for 문 돌면서 그 key의 id가 같으면 삭제하기
                    for (ds in foodsSnapShot.children) {
                        val key = ds.key.toString()
                        val selectedId = ds.getValue(Foods::class.java)?.id
                        if (foods.id == selectedId) {
                            databaseReference.child("Foods").child(key).removeValue()
                            break
                        }
                    }
                }
            }
        })

        binding?.mRecyclerView?.adapter = adapter

        //버튼 클릭시 ChartActivity로 이통
        initChartBtn(binding)

    }
    private fun makeKakaoTemplate(): FeedTemplate {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "오늘의 디저트",
                description = "#케익 #딸기 #삼평동 #카페 #분위기 #소개팅",
                imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            itemContent = ItemContent(
                profileText = "Kakao",
                profileImageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                titleImageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                titleImageText = "Cheese cake",
                titleImageCategory = "Cake",
                items = listOf(
                    ItemInfo(item = "cake1", itemOp = "1000원"),
                    ItemInfo(item = "cake2", itemOp = "2000원"),
                    ItemInfo(item = "cake3", itemOp = "3000원"),
                    ItemInfo(item = "cake4", itemOp = "4000원"),
                    ItemInfo(item = "cake5", itemOp = "5000원")
                ),
                sum = "Total",
                sumOp = "15000원"
            ),
            social = Social(
                likeCount = 286,
                commentCount = 45,
                sharedCount = 845
            ),
            buttons = listOf(
                Button(
                    "웹으로 보기",
                    Link(
                        webUrl = "https://developers.kakao.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                        iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                    )
                )
            )
        )
        return defaultFeed
    }

    private fun startKakaoLink(defaultFeed: FeedTemplate) {
        if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(requireContext(), defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡 공유 실패", error)
                } else if (sharingResult != null) {
                    Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                    Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

            // CustomTabs으로 웹 브라우저 열기

            // 1. CustomTabsServiceConnection 지원 브라우저 열기
            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
            try {
                KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
            } catch (e: UnsupportedOperationException) {
                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
            // ex) 다음, 네이버 등
            try {
                KakaoCustomTabsClient.open(requireContext(), sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }

    private fun initChartBtn(binding: FragmentHistoryBinding?) {
        binding?.btnChart?.setOnClickListener {
            val food = foodsList.map { s -> s.name }.toCollection(ArrayList())
            val bundle = Bundle().apply {
                putStringArrayList("foodsList", foodsList.map { s -> s.name }.toCollection(ArrayList<String>()))
            }
            findNavController().navigate(R.id.action_historyFragment_to_chartFragment, bundle)
        }
    }


    private fun initFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //late init
                mySnapShot = snapshot
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}