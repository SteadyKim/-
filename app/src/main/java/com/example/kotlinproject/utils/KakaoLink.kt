package com.example.kotlinproject.utils

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.kotlinproject.enum.FoodNames
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

class KakaoLink: AppCompatActivity() {
    companion object {

        fun init(context: Context) {
            KakaoSdk.init(context, "a3371d68064ad62c0ef12f967df3741c")
        }

        fun getKakaoTemplate(selectedFood: String): FeedTemplate {
            val defaultFeed = FeedTemplate(
                content = Content(
                    title = "랜덤 음식",
                    description = selectedFood,
                    imageUrl = when(selectedFood){
                        FoodNames.BIBIM_BAP -> "https://user-images.githubusercontent.com/88755733/203039310-716be5d5-8770-419d-890b-f32b4a925bd4.png"
                        FoodNames.BUCHIM_GAE -> "https://user-images.githubusercontent.com/88755733/203727863-366fee61-d4b0-4427-993b-ec99d1e9f7bd.png"
                        FoodNames.CHEONGGUCK_JANG -> "https://user-images.githubusercontent.com/88755733/203728045-61c24621-260b-4591-b4f7-2f52826254bf.png"
                        FoodNames.DAK_BAL -> "https://user-images.githubusercontent.com/88755733/203728269-a553796c-f77c-420c-9474-1dd285a511e5.png"
                        FoodNames.CHICKEN -> "https://user-images.githubusercontent.com/88755733/203728345-11a34913-a151-4b9c-bd00-28714782ec48.png"
                        FoodNames.DDEK_BOKKI -> "https://user-images.githubusercontent.com/88755733/203728383-6c5041e5-191c-404e-b2e9-bed5fb96a536.png"
                        FoodNames.DO_SI_RAK -> "https://user-images.githubusercontent.com/88755733/203728412-657b8422-d137-4863-924e-1516c01ca60f.png"
                        FoodNames.DOEN_JANG_JJIGAE -> "https://user-images.githubusercontent.com/88755733/203728459-644bd377-6c5b-4c6e-a824-1951367fd46a.png"
                        FoodNames.DON_GAS -> "https://user-images.githubusercontent.com/88755733/203728499-deea3a89-eeef-45e7-9eac-e67a711f4569.png"
                        FoodNames.GALBITANG -> "https://user-images.githubusercontent.com/88755733/203728571-d2a0d41f-63de-483e-b6af-8be4f845aa1e.png"

                        FoodNames.GIM_BAP -> "https://user-images.githubusercontent.com/88755733/203728661-cc6d4818-f4c9-4593-99aa-92208413a6fa.png"
                        FoodNames.GOB_CHANG -> "https://user-images.githubusercontent.com/88755733/203728713-44a41164-a7a7-4b7b-a8e1-b403f3ee5306.png"
                        FoodNames.GUG_BAB -> "https://user-images.githubusercontent.com/88755733/203728754-a781fba8-5879-414f-98dc-b22efdc69a4c.png"
                        FoodNames.HAEJANG_GUG -> "https://user-images.githubusercontent.com/88755733/203728781-604e571b-c3d1-45d7-af65-9bf9f11b5b5a.png"
                        FoodNames.HAMBURGER -> "https://user-images.githubusercontent.com/88755733/203728960-5fb2303e-9342-4273-9fdd-ef0637bcb40e.png"
                        FoodNames.HOTDOG -> "https://user-images.githubusercontent.com/88755733/203729004-6fbb1469-6c56-4769-b801-ccf479f4a473.png"
                        FoodNames.JAJJANG -> "https://user-images.githubusercontent.com/88755733/203729037-a301e21f-e14e-4e0e-b82c-bb502ffce849.png"
                        FoodNames.JEYUGBOKK_EUM -> "https://user-images.githubusercontent.com/88755733/203729081-130553f3-c338-446d-a84c-85944bb21fce.png"
                        FoodNames.JJAMBONG -> "https://user-images.githubusercontent.com/88755733/203729528-4b4e0438-5da9-412b-8bce-5c420497aad5.jpg"
                        FoodNames.JUKKUMI -> "https://user-images.githubusercontent.com/88755733/203729594-6fb52305-da2d-416d-86d3-adcb1f2461b8.png"
                        FoodNames.KALGUGSU -> "https://user-images.githubusercontent.com/88755733/203731850-7a942863-8eb0-47b4-ae9e-768e51c65332.png"
                        FoodNames.MAN_DO -> "https://user-images.githubusercontent.com/88755733/203729690-d120fd04-e47f-4967-9f3e-38506405bdda.png"
                        FoodNames.MARA_TANG -> "https://user-images.githubusercontent.com/88755733/203729791-902f6ba2-df6a-4f1b-b459-43ce04859042.png"
                        FoodNames.OMURICE -> "https://user-images.githubusercontent.com/88755733/203729870-a31cf1a1-a521-41f4-95ef-d49b401928bf.png"
                        FoodNames.PASTA -> "https://user-images.githubusercontent.com/88755733/203729918-66294b95-a9e5-4ae4-8c22-9e2e0997be44.png"
                        FoodNames.PIZZA -> "https://user-images.githubusercontent.com/88755733/203730010-114f48aa-ccf0-41e0-ba4f-1402d40b2b30.png"
                        FoodNames.RAMEN -> "https://user-images.githubusercontent.com/88755733/203730159-df19e927-d55d-46c5-a3eb-539829491d41.jpg"
                        FoodNames.SAM_GYE_TANG -> "https://user-images.githubusercontent.com/88755733/203730202-ee3db4f2-8320-47b4-a7de-316e178b07b1.png"
                        FoodNames.SAM_GYEOB_SAL -> "https://user-images.githubusercontent.com/88755733/203730444-a204898b-257b-4a78-bcb0-f7a21ed0bb03.png"
                        FoodNames.SANDWICH -> "https://user-images.githubusercontent.com/88755733/203730495-ed0df7f7-fdb7-437e-9b51-99fd1201841a.pn"

                        FoodNames.SASHIMI -> "https://user-images.githubusercontent.com/88755733/203730625-860f7bba-45b8-4a1b-8072-42be48d338d7.png"
                        FoodNames.SOYED_CRAB -> "https://user-images.githubusercontent.com/88755733/203730671-5f0c69f8-685f-4905-b390-017fcfa9863e.png"
                        FoodNames.SSAL_GUKSU -> "https://user-images.githubusercontent.com/88755733/203730770-a9299231-f5a5-4a37-8bcb-97e8a8b3d7ef.png"

                        FoodNames.SUNDAEBOKK -> "https://user-images.githubusercontent.com/88755733/203730932-26ba5963-71cc-4332-8aba-ce1d7f6f9ba9.png"
                        FoodNames.YANG_KKO_CHI -> "https://user-images.githubusercontent.com/88755733/203731212-0b54f0c0-8f5e-478c-8c1c-df9b021ab85a.png"

                        else -> "https://user-images.githubusercontent.com/88755733/203731133-89de0050-9a45-487e-9a76-758cc467fcc0.png"
                    },
                    link = Link(

                    )
                )
            )
            return defaultFeed
        }

        fun startKakaoLink(defaultFeed: FeedTemplate, context: Context) {
            if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
                // 카카오톡으로 카카오톡 공유 가능
                ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
                    if (error != null) {
                        Log.e(ContentValues.TAG, "카카오톡 공유 실패", error)
                    } else if (sharingResult != null) {
                        Log.d(ContentValues.TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                        startActivity(context, sharingResult.intent, Bundle())

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
                    KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
                } catch (e: UnsupportedOperationException) {
                    // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
                }

                // 2. CustomTabsServiceConnection 미지원 브라우저 열기
                // ex) 다음, 네이버 등
                try {
                    KakaoCustomTabsClient.open(context, sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
                }
            }
        }
    }



}