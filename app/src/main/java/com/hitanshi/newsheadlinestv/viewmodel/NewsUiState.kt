package com.hitanshi.newsheadlinestv.viewmodel

import com.hitanshi.newsheadlinestv.data.model.Article

data class NewsUiState(

    val isLoading: Boolean = false,

    val articles: List<Article> = emptyList(),

    val error: String? = null

)