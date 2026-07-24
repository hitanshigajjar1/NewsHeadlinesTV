package com.hitanshi.newsheadlinestv.viewmodel


import com.hitanshi.newsheadlinestv.data.model.Article

sealed class UiState {

    object Loading : UiState()

    data class Success(

        val articles: List<Article>

    ) : UiState()

    data class Error(

        val message: String

    ) : UiState()

}