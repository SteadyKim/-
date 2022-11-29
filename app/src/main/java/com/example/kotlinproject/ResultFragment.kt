package com.example.kotlinproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.kotlinproject.databinding.FragmentResultBinding
import com.example.kotlinproject.db.AppDatabase
import com.example.kotlinproject.utils.RandomFood.Companion.ANYTHINGFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.ASIANFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.CHINESEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.JAPANESEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.KOREANFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.MEATFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.NOODLEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.RICEFOOD
import com.example.kotlinproject.utils.RandomFood.Companion.WESTERNFOOD
import com.example.kotlinproject.db.entity.Foods
import com.example.kotlinproject.utils.KakaoLink
import com.example.kotlinproject.viewmodel.MyViewModel
import java.time.LocalDate
import java.util.*

class ResultFragment : Fragment() {
    var db: AppDatabase? = null

    var flag = 0
    var binding : FragmentResultBinding? = null
    val viewModel: MyViewModel by activityViewModels()

    var imageStatus = -1
    lateinit var selectedFood : String

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

            // food 객체 만들기
            var random_uuid = UUID.randomUUID()
            val today: LocalDate = LocalDate.now()
            val food = Foods(random_uuid.toString(), selectedFood, today.toString())

            viewModel.addFirebaseData(food)

            val bundle = Bundle().apply {
                putString("Food",selectedFood)
            }
            //setFragmentResult("Food", bundle)
            // 2.내부 DB에 저장
            db = AppDatabase.getInstance(requireContext())
            db?.FoodsDao()?.insertAll(food)
            findNavController().navigate(R.id.action_resultFragment_to_mapFragment, bundle)
        }

        //TODO 도혁님 redo 작업 부탁드려요
        binding?.btnRedo?.setOnClickListener {
            setImage()
        }

        binding?.btnShare?.setOnClickListener {

            KakaoLink.init(requireContext())
            val kakaoFeed = KakaoLink.getKakaoTemplate(selectedFood)
            KakaoLink.startKakaoLink(kakaoFeed, requireContext())

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
            val index = (1..anything.size).random()
            selectedFood = anything[index - 1]
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
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
                17 -> R.drawable.select_sam_gyeob_sal
                else -> R.drawable.select_sundaebokk_eum
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }

        if (chinese != null) {
            val randomNumber = (1..chinese.size).random()
            selectedFood = chinese[randomNumber - 1]
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
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
            binding?.txtResult?.text= selectedFood
            val randomResource = when (randomNumber) {
                1 -> R.drawable.select_bibim_bap
                2 -> R.drawable.select_cheong_guk_jang
                3 -> R.drawable.select_doen_jang_jjigae
                4 -> R.drawable.select_do_si_rak
                5 -> R.drawable.select_galbitang
                6 -> R.drawable.select_gim_bap
                7 -> R.drawable.select_haejang_gug
                8 -> R.drawable.select_gug_bab
                9 -> R.drawable.select_omurice
                else -> R.drawable.select_sam_gye_tang
            }
            imageStatus = randomResource
            binding?.imgResult?.setImageResource(randomResource)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}