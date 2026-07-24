package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel
) {

    val state by viewModel.uiState.collectAsState()

    var refreshing by remember { mutableStateOf(false) }

    // FocusRequester for the very first card so the row has an
    // initial focus target as soon as data is loaded.
    val firstItemFocusRequester = remember { FocusRequester() }

    // Request focus on the first card whenever a fresh article list arrives.
    LaunchedEffect(state.articles) {
        if (state.articles.isNotEmpty()) {
            runCatching { firstItemFocusRequester.requestFocus() }
        }
    }

    // Once a refresh finishes, drop the "Refreshing..." banner.
    LaunchedEffect(state.isLoading) {
        if (!state.isLoading) {
            refreshing = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {

        Text(
            text = "News Headlines",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp, top = 32.dp, bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
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
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {

                    LazyRow(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusGroup(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.Top,
                        contentPadding = PaddingValues(48.dp)
                    ) {

                        itemsIndexed(
                            items = state.articles,
                            key = { index, article -> article.url ?: index }
                        ) { index, article ->

                            NewsCard(
                                article = article,
                                focusRequester = if (index == 0) firstItemFocusRequester else null,
                                onRefresh = {
                                    if (!state.isLoading) {
                                        refreshing = true
                                        viewModel.refreshNews()
                                    }
                                }
                            )
                        }
                    }
                }
            }

            if (refreshing) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)) {
                    Text(
                        text = "Refreshing News...",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}