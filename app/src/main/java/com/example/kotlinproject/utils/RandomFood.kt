package com.example.kotlinproject.utils

import com.example.kotlinproject.enum.FoodNames.Companion.BIBIM_BAP
import com.example.kotlinproject.enum.FoodNames.Companion.BUCHIM_GAE
import com.example.kotlinproject.enum.FoodNames.Companion.CHEONGGUCK_JANG
import com.example.kotlinproject.enum.FoodNames.Companion.CHICKEN
import com.example.kotlinproject.enum.FoodNames.Companion.DAK_BAL
import com.example.kotlinproject.enum.FoodNames.Companion.DDEK_BOKKI
import com.example.kotlinproject.enum.FoodNames.Companion.DOEN_JANG_JJIGAE
import com.example.kotlinproject.enum.FoodNames.Companion.DON_GAS
import com.example.kotlinproject.enum.FoodNames.Companion.DO_SI_RAK
import com.example.kotlinproject.enum.FoodNames.Companion.GALBITANG
import com.example.kotlinproject.enum.FoodNames.Companion.GIM_BAP
import com.example.kotlinproject.enum.FoodNames.Companion.GOB_CHANG
import com.example.kotlinproject.enum.FoodNames.Companion.GUG_BAB
import com.example.kotlinproject.enum.FoodNames.Companion.HAEJANG_GUG
import com.example.kotlinproject.enum.FoodNames.Companion.HAMBURGER
import com.example.kotlinproject.enum.FoodNames.Companion.HOTDOG
import com.example.kotlinproject.enum.FoodNames.Companion.JAJJANG
import com.example.kotlinproject.enum.FoodNames.Companion.JEYUGBOKK_EUM
import com.example.kotlinproject.enum.FoodNames.Companion.JJAMBONG
import com.example.kotlinproject.enum.FoodNames.Companion.JUKKUMI
import com.example.kotlinproject.enum.FoodNames.Companion.KALGUGSU
import com.example.kotlinproject.enum.FoodNames.Companion.MAN_DO
import com.example.kotlinproject.enum.FoodNames.Companion.MARA_TANG
import com.example.kotlinproject.enum.FoodNames.Companion.OMURICE
import com.example.kotlinproject.enum.FoodNames.Companion.PASTA
import com.example.kotlinproject.enum.FoodNames.Companion.PIZZA
import com.example.kotlinproject.enum.FoodNames.Companion.RAMEN
import com.example.kotlinproject.enum.FoodNames.Companion.SAM_GYEOB_SAL
import com.example.kotlinproject.enum.FoodNames.Companion.SAM_GYE_TANG
import com.example.kotlinproject.enum.FoodNames.Companion.SANDWICH
import com.example.kotlinproject.enum.FoodNames.Companion.SASHIMI
import com.example.kotlinproject.enum.FoodNames.Companion.SOYED_CRAB
import com.example.kotlinproject.enum.FoodNames.Companion.SSAL_GUKSU
import com.example.kotlinproject.enum.FoodNames.Companion.SUNDAEBOKK
import com.example.kotlinproject.enum.FoodNames.Companion.YANG_KKO_CHI
import com.example.kotlinproject.enum.FoodNames.Companion.YUG_HOE

class RandomFood {
     companion object {
          val ANYTHINGFOOD = "anythingFood"
          val KOREANFOOD = "koreanFood"
          val CHINESEFOOD = "chineseFood"
          val WESTERNFOOD = "westernFood"
          val ASIANFOOD = "asianFood"
          val JAPANESEFOOD = "japanenseFood"
          val NOODLEFOOD = "noodleFood"
          val MEATFOOD = "meatFood"
          val RICEFOOD = "riceFood"

          fun getAnyThing(): ArrayList<String> {

               val anything: ArrayList<String> = ArrayList()

               anything.add(BIBIM_BAP)
               anything.add(BUCHIM_GAE)
               anything.add(CHEONGGUCK_JANG)
               anything.add(CHICKEN)
               anything.add(DAK_BAL)
               anything.add(DDEK_BOKKI)
               anything.add(DO_SI_RAK)
               anything.add(DOEN_JANG_JJIGAE)
               anything.add(DON_GAS)
               anything.add(GALBITANG)

               anything.add(GIM_BAP)
               anything.add(GOB_CHANG)
               anything.add(GUG_BAB)
               anything.add(HAEJANG_GUG)
               anything.add(HAMBURGER)
               anything.add(HOTDOG)
               anything.add(JAJJANG)
               anything.add(JJAMBONG)
               anything.add(JUKKUMI)

               anything.add(KALGUGSU)
               anything.add(MAN_DO)
               anything.add(MARA_TANG)
               anything.add(OMURICE)
               anything.add(PASTA)
               anything.add(PIZZA)
               anything.add(RAMEN)
               anything.add(SAM_GYE_TANG)
               anything.add(SAM_GYEOB_SAL)
               anything.add(SANDWICH)

               anything.add(SASHIMI)
               anything.add(SOYED_CRAB)
               anything.add(SSAL_GUKSU)

               anything.add(SUNDAEBOKK)
               anything.add(YANG_KKO_CHI)
               anything.add(YUG_HOE)
               anything.add(JEYUGBOKK_EUM)

               return anything
          }

          fun getKoreanFood(): ArrayList<String> {

               val korean: ArrayList<String> = ArrayList()

               korean.add(BIBIM_BAP)
               korean.add(BUCHIM_GAE)
               korean.add(CHEONGGUCK_JANG)
               korean.add(DAK_BAL)
               korean.add(DDEK_BOKKI)
               korean.add(DO_SI_RAK)
               korean.add(DOEN_JANG_JJIGAE)
               korean.add(GALBITANG)
               korean.add(GIM_BAP)
               korean.add(GOB_CHANG)

               korean.add(GUG_BAB)
               korean.add(HAEJANG_GUG)
               korean.add(JEYUGBOKK_EUM)
               korean.add(JUKKUMI)
               korean.add(KALGUGSU)
               korean.add(SAM_GYE_TANG)
               korean.add(SAM_GYEOB_SAL)
               korean.add(SUNDAEBOKK)

               return korean
          }

          fun getChineseFood(): ArrayList<String> {

               val chinese: ArrayList<String> = ArrayList()
               chinese.add(JAJJANG)
               chinese.add(JJAMBONG)
               chinese.add(MAN_DO)
               chinese.add(MARA_TANG)
               chinese.add(YANG_KKO_CHI)

               return chinese
          }

          fun getWesternFood(): ArrayList<String> {

               val western: ArrayList<String> = ArrayList()
               western.add(DON_GAS)
               western.add(HAMBURGER)
               western.add(HOTDOG)
               western.add(OMURICE)
               western.add(PASTA)
               western.add(PIZZA)
               western.add(SANDWICH)

               return western
          }

          fun getAsianFood(): ArrayList<String> {

               val asian: ArrayList<String> = ArrayList()
               asian.add(BIBIM_BAP)
               asian.add(BUCHIM_GAE)
               asian.add(CHICKEN)
               asian.add(DAK_BAL)
               asian.add(DO_SI_RAK)
               asian.add(GIM_BAP)
               asian.add(GOB_CHANG)
               asian.add(JAJJANG)
               asian.add(JUKKUMI)
               asian.add(KALGUGSU)

               asian.add(MAN_DO)
               asian.add(MARA_TANG)
               asian.add(RAMEN)
               asian.add(SAM_GYEOB_SAL)
               asian.add(SOYED_CRAB)
               asian.add(SSAL_GUKSU)
               asian.add(JJAMBONG)
               asian.add(YANG_KKO_CHI)

               return asian
          }

          fun getJapaneseFood(): ArrayList<String> {

               val japanese: ArrayList<String> = ArrayList()
               japanese.add(DON_GAS)
               japanese.add(RAMEN)
               japanese.add(SASHIMI)
               japanese.add(SOYED_CRAB)

               return japanese
          }

          fun getNoodleFood(): ArrayList<String> {

               val noodle: ArrayList<String> = ArrayList()
               noodle.add(JAJJANG)
               noodle.add(JJAMBONG)
               noodle.add(KALGUGSU)
               noodle.add(MARA_TANG)
               noodle.add(PASTA)
               noodle.add(RAMEN)
               noodle.add(SSAL_GUKSU)

               return noodle
          }

          fun getMeatFood(): ArrayList<String> {

               val meat: ArrayList<String> = ArrayList()
               meat.add(CHICKEN)
               meat.add(DAK_BAL)
               meat.add(JEYUGBOKK_EUM)
               meat.add(SAM_GYEOB_SAL)
               meat.add(YANG_KKO_CHI)
               meat.add(YUG_HOE)

               return meat
          }

          fun getRiceFood(): ArrayList<String> {

               val rice: ArrayList<String> = ArrayList()
               rice.add(BIBIM_BAP)
               rice.add(CHEONGGUCK_JANG)
               rice.add(DOEN_JANG_JJIGAE)
               rice.add(DO_SI_RAK)
               rice.add(GALBITANG)
               rice.add(GIM_BAP)
               rice.add(HAEJANG_GUG)
               rice.add(GUG_BAB)
               rice.add(OMURICE)
               rice.add(SAM_GYE_TANG)

               return rice
          }

     }
}