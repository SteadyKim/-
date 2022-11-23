package com.example.kotlinproject

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.databinding.FragmentResultBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.db.RandomFood.Companion.ANYTHINGFOOD
import com.example.kotlinproject.db.RandomFood.Companion.ASIANFOOD
import com.example.kotlinproject.db.RandomFood.Companion.CHINESEFOOD
import com.example.kotlinproject.db.RandomFood.Companion.JAPANESEFOOD
import com.example.kotlinproject.db.RandomFood.Companion.KOREANFOOD
import com.example.kotlinproject.db.RandomFood.Companion.MEATFOOD
import com.example.kotlinproject.db.RandomFood.Companion.NOODLEFOOD
import com.example.kotlinproject.db.RandomFood.Companion.RICEFOOD
import com.example.kotlinproject.db.RandomFood.Companion.WESTERNFOOD
import com.example.kotlinproject.db.entity.Foods
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.*
import java.time.LocalDate
import java.util.*

class ResultFragment : Fragment() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference : DatabaseReference = database.getReference()
    var db: AppDatabase? = null

    var flag = 0
    var binding : FragmentResultBinding? = null

    var imageStatus = -1
    lateinit var selectedFood : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            anything = it.getStringArrayList("anything")
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(flag == 0){
            setImage()
        }else {
            setCurrentImage()
        }
        flag++


        binding?.btnMap?.setOnClickListener {

            // 1.firebase 에 저장
            var random_uuid = UUID.randomUUID()
            val today: LocalDate = LocalDate.now()
            val food = Foods(random_uuid.toString(), selectedFood, today.toString())
            databaseReference.child("Foods").push().setValue(food)

            val bundle = Bundle()
            bundle.putString("Food", selectedFood)

            // 2.내부 DB에 저장
            db = AppDatabase.getInstance(requireContext())
            db?.FoodsDao()?.insertAll(food)
            findNavController().navigate(R.id.action_resultFragment_to_mapFragment, bundle)
        }

        //TODO 도혁님 redo 작업 부탁드려요
        binding?.btnRedo?.setOnClickListener {  }

        binding?.btnShare?.setOnClickListener {
            KakaoSdk.init(requireContext(), "a3371d68064ad62c0ef12f967df3741c")
            val defaultFeed = makeKakaoTemplate()
            //카카오톡 설치 여부 확인
            startKakaoLink(defaultFeed)
        }
    }

    private fun setCurrentImage() {
        binding?.imgResult?.setImageResource(imageStatus)
    }

    private fun setImage() {
        val anything = arguments?.getStringArrayList(ANYTHINGFOOD)
        val korean = arguments?.getStringArrayList(KOREANFOOD)
        val chinese = arguments?.getStringArrayList(CHINESEFOOD)
        val western = arguments?.getStringArrayList(WESTERNFOOD)
        val asian = arguments?.getStringArrayList(ASIANFOOD)
        val japanese = arguments?.getStringArrayList(JAPANESEFOOD)
        val noodle = arguments?.getStringArrayList(NOODLEFOOD)
        val meat = arguments?.getStringArrayList(MEATFOOD)
        val rice = arguments?.getStringArrayList(RICEFOOD)

        if (anything != null) {
            val index = (1..anything?.size!!).random()
            selectedFood = anything[index - 1]

            val randomResource = when (index) {
                1 -> R.drawable.select_bibim_bap
                2 -> R.drawable.select_buchim_gae
                3 -> R.drawable.select_cheong_guk_jang
                4 -> R.drawable.select_chicken
                5 -> R.drawable.select_dak_bal
                6 -> R.drawable.select_ddek_bokki
                7 -> R.drawable.select_do_si_rak
                8 -> R.drawable.select_doen_jang_jjigae
                9 -> R.drawable.select_don_gas
                10 -> R.drawable.select_galbitang

                11 -> R.drawable.select_gim_bap
                12 -> R.drawable.select_gob_chang
                13 -> R.drawable.select_gug_bab
                14 -> R.drawable.select_haejang_gug
                15 -> R.drawable.select_hamburger
                16 -> R.drawable.select_hotdog
                17 -> R.drawable.select_jajjang
                18 -> R.drawable.select_jjambong
                19 -> R.drawable.select_jukkumi

                20 -> R.drawable.select_kalgugsu
                21 -> R.drawable.select_man_do
                22 -> R.drawable.select_mara_tang
                23 -> R.drawable.select_omurice
                24 -> R.drawable.select_pasta
                25 -> R.drawable.select_pizza
                26 -> R.drawable.select_ramen
                27 -> R.drawable.select_sam_gye_tang
                28 -> R.drawable.select_sam_gyeob_sal
                29 -> R.drawable.select_sandwich

                30 -> R.drawable.select_sashimi
                31 -> R.drawable.select_soyed_crab
                32 -> R.drawable.select_ssal_guksu

                33 -> R.drawable.select_sundaebokk_eum
                34 -> R.drawable.select_yang_kko_chi
                35 -> R.drawable.select_yug_hoe
                else -> R.drawable.select_jeyugbokk_eum
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (korean != null) {
            val randomNumber = (1..korean.size).random()
            selectedFood = korean[randomNumber - 1]

            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_bibim_bap
                2 -> R.drawable.select_buchim_gae
                3 -> R.drawable.select_cheong_guk_jang
                4 -> R.drawable.select_dak_bal
                5 -> R.drawable.select_ddek_bokki
                6 -> R.drawable.select_do_si_rak
                7 -> R.drawable.select_doen_jang_jjigae
                8 -> R.drawable.select_galbitang
                9 -> R.drawable.select_gim_bap
                10 -> R.drawable.select_gob_chang

                11 -> R.drawable.select_gug_bab
                12 -> R.drawable.select_haejang_gug
                13 -> R.drawable.select_jeyugbokk_eum
                14 -> R.drawable.select_jukkumi
                15 -> R.drawable.select_kalgugsu
                16 -> R.drawable.select_sam_gye_tang
                17 -> R.drawable.select_galbitang
                18 -> R.drawable.select_sam_gyeob_sal
                else -> R.drawable.select_sundaebokk_eum
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (chinese != null) {
            val randomNumber = (1..chinese.size).random()
            selectedFood = chinese[randomNumber - 1]

            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_jajjang
                2 -> R.drawable.select_jjambong
                3 -> R.drawable.select_man_do
                4 -> R.drawable.select_mara_tang
                else -> R.drawable.select_yang_kko_chi
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (western != null) {
            val randomNumber = (1..western.size).random()
            selectedFood = western[randomNumber - 1]

            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_don_gas
                2 -> R.drawable.select_hamburger
                3 -> R.drawable.select_hotdog
                4 -> R.drawable.select_omurice
                5 -> R.drawable.select_pasta
                6 -> R.drawable.select_pizza
                else -> R.drawable.select_sandwich

            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (asian != null) {
            val randomNumber = (1..asian.size).random()
            selectedFood = asian[randomNumber - 1]

            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_bibim_bap
                2 -> R.drawable.select_buchim_gae
                3 -> R.drawable.select_chicken
                4 -> R.drawable.select_dak_bal
                5 -> R.drawable.select_do_si_rak
                6 -> R.drawable.select_gim_bap
                7 -> R.drawable.select_gob_chang
                8 -> R.drawable.select_jajjang
                9 -> R.drawable.select_jukkumi
                10 -> R.drawable.select_kalgugsu

                11 -> R.drawable.select_man_do
                12 -> R.drawable.select_mara_tang
                13 -> R.drawable.select_ramen
                14 -> R.drawable.select_sam_gyeob_sal
                15 -> R.drawable.select_soyed_crab
                16 -> R.drawable.select_ssal_guksu
                17 -> R.drawable.select_jjambong
                else -> R.drawable.select_yang_kko_chi
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (japanese != null) {
            val randomNumber = (1..japanese.size).random()
            selectedFood = japanese[randomNumber - 1]


            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_don_gas
                2 -> R.drawable.select_ramen
                3 -> R.drawable.select_sashimi
                else -> R.drawable.select_soyed_crab
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (noodle != null) {
            val randomNumber = (1..noodle.size).random()
            selectedFood = noodle[randomNumber - 1]


            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_jajjang
                2 -> R.drawable.select_jjambong
                3 -> R.drawable.select_kalgugsu
                4 -> R.drawable.select_mara_tang
                5 -> R.drawable.select_pasta
                6 -> R.drawable.select_ramen
                else -> R.drawable.select_ssal_guksu
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (meat != null) {
            val randomNumber = (1..meat.size).random()
            selectedFood = meat[randomNumber - 1]

            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_chicken
                2 -> R.drawable.select_dak_bal
                3 -> R.drawable.select_jeyugbokk_eum
                4 -> R.drawable.select_sam_gyeob_sal
                5 -> R.drawable.select_yang_kko_chi
                else -> R.drawable.select_yug_hoe
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (rice != null) {
            val randomNumber = (1..rice.size).random()
            selectedFood = rice[randomNumber - 1]

            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_bibim_bap
                2 -> R.drawable.select_cheong_guk_jang
                3 -> R.drawable.select_do_si_rak
                4 -> R.drawable.select_galbitang
                5 -> R.drawable.select_gim_bap
                6 -> R.drawable.select_haejang_gug
                7 -> R.drawable.select_gug_bab
                8 -> R.drawable.select_omurice
                else -> R.drawable.select_sam_gye_tang
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }
    }
    private fun makeKakaoTemplate(): FeedTemplate {
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "랜덤 음식",
                description = "비빔밥",
                imageUrl = "https://user-images.githubusercontent.com/88755733/203039310-716be5d5-8770-419d-890b-f32b4a925bd4.png",
                link = Link(

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
                    Log.e(ContentValues.TAG, "카카오톡 공유 실패", error)
                } else if (sharingResult != null) {
                    Log.d(ContentValues.TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w(ContentValues.TAG, "Warning Msg: ${sharingResult.warningMsg}")
                    Log.w(ContentValues.TAG, "Argument Msg: ${sharingResult.argumentMsg}")
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
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}