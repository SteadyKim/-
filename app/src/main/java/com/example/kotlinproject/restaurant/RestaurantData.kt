package com.example.kotlinproject.restaurant

data class RestaurantData(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)