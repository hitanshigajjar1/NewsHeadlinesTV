package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel
) {

    val state by viewModel.uiState.collectAsState()

    var pressStart by remember {
        mutableLongStateOf(0L)
    }

    val firstItemFocusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(state.articles.size) {
        if (state.articles.isNotEmpty()) {
            firstItemFocusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (state.isLoading) {

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .focusable()
                .onPreviewKeyEvent {

                    if (it.key == Key.DirectionDown) {

                        when (it.type) {

                            KeyEventType.KeyDown -> {

                                pressStart = System.currentTimeMillis()
                                false

                            }

                            KeyEventType.KeyUp -> {

                                val duration =
                                    System.currentTimeMillis() - pressStart

                                if (duration >= 700) {

                                    viewModel.refreshNews()

                                    true

                                } else {

                                    false

                                }

                            }

                            else -> false
                        }

                    } else {

                        false

                    }

                }

        ) {

            when {

                state.isLoading && state.articles.isEmpty() -> {

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

                        itemsIndexed(state.articles) { index, article ->

                            if (index == 0) {

                                Box(
                                    modifier = Modifier
                                        .focusRequester(firstItemFocusRequester)
                                ) {

                                    NewsCard(article)

                                }

                            } else {

                                NewsCard(article)

                            }

                        }

                    }

                }

            }

        }

    }

}