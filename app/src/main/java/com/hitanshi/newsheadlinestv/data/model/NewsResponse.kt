package com.hitanshi.newsheadlinestv.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("articles")
    val articles: List<ArticleDto>
)