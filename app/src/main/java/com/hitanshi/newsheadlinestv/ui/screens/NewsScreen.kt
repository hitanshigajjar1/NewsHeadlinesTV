package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel
) {

    val state by viewModel.uiState.collectAsState()

    var pressStart by remember {
        mutableStateOf(0L)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .focusable()
            .onKeyEvent {

                if (it.key == Key.DirectionDown) {

                    when (it.type) {

                        KeyEventType.KeyDown -> {

                            pressStart = System.currentTimeMillis()

                            false
                        }

                        KeyEventType.KeyUp -> {

                            val duration =
                                System.currentTimeMillis() - pressStart

                            if (duration > 700) {

                                viewModel.refreshNews()

                            }

                            false
                        }

                        else -> false
                    }

                } else {

                    false

                }

            }

    ) {

        when {

            state.isLoading -> {

                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            state.error != null -> {

                Text(
                    text = state.error!!,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            else -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    items(state.articles) {

                        NewsCard(it)

                    }

                }

            }

        }

    }

}