package com.hitanshi.newsheadlinestv.data.model

data class Article(

    val id: String,

    val title: String,

    val description: String,

    val imageUrl: String?,

    val source: String,

    val publishedAt: String,

    val url: String,

    val category: String
)