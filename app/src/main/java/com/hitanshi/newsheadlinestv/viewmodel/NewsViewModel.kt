package com.hitanshi.newsheadlinestv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitanshi.newsheadlinestv.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())

    val uiState: StateFlow<NewsUiState> =
        _uiState.asStateFlow()

    init {
        refreshNews()
    }

    fun refreshNews() {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    error = null
                )

            try {

                val news = repository.getHeadlines()

                _uiState.value =
                    NewsUiState(
                        isLoading = false,
                        articles = news
                    )

            } catch (e: Exception) {

                _uiState.value =
                    NewsUiState(
                        isLoading = false,
                        error = e.message ?: "Unknown Error"
                    )

            }

        }

    }

}