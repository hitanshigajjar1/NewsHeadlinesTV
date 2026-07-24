package com.hitanshi.newsheadlinestv.utils

object Constants {

    const val BASE_URL = "https://newsapi.org/v2/"

    // Replace with your own API key
    const val API_KEY = "ADD NEWS API KEY HERE"

    const val COUNTRY = "us"

    const val PAGE_SIZE = 30

    val CATEGORIES = listOf(
        "general",
        "technology",
        "business"
    )

    const val LONG_PRESS_DURATION = 600L

    const val SUMMARY_COLLAPSED_LINES = 3
}