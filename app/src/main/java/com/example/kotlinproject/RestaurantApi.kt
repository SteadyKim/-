package com.example.kotlinproject

import com.example.kotlinproject.restaurant.RestaurantData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

const val BASE_URL = "https://maps.googleapis.com/"
const val API_KEY = "AIzaSyDNfNqFjQEOWNmDG4j7xJfTuU99-zcgc4s"

    interface NearbySearchAPI {
        @GET
        fun getRestaurant(@Url url: String): Call<RestaurantData>
    }

    object RetrofitObject {
        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApiService(): NearbySearchAPI {
            return getRetrofit().create(NearbySearchAPI::class.java)
        }

    }