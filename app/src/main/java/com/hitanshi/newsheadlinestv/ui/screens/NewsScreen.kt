package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel
) {

    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        when {

            state.isLoading -> {

                CircularProgressIndicator()

            }

            state.error != null -> {

                Text(state.error!!)

            }

            else -> {

                Text(
                    "Loaded ${state.articles.size} Articles"
                )

            }

        }

    }

}