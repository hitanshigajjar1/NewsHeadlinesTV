package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hitanshi.newsheadlinestv.data.model.Article
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel
) {

    val state by viewModel.uiState.collectAsState()

    var refreshing by remember { mutableStateOf(false) }

    // Which article's full summary is currently shown in the AlertDialog.
    // null == dialog dismissed.
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    // FocusRequester for the very first card so the row has an
    // initial focus target as soon as data is loaded.
    val firstItemFocusRequester = remember { FocusRequester() }

    // FocusRequester for the dialog's "Close" button, so DPAD OK works
    // immediately once the dialog opens instead of leaving focus stuck
    // on the card underneath.
    val closeButtonFocusRequester = remember { FocusRequester() }

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

    // Whenever a dialog opens, move DPAD focus onto its close button.
    LaunchedEffect(selectedArticle) {
        if (selectedArticle != null) {
            runCatching { closeButtonFocusRequester.requestFocus() }
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
                                },
                                onArticleClick = {
                                    selectedArticle = article
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
                        .padding(20.dp)) {
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

    // Full-summary dialog. Kept outside the Box/LazyRow so it renders as a
    // true overlay above everything, including the "Refreshing..." banner.
    selectedArticle?.let { article ->
        AlertDialog(
            onDismissRequest = { selectedArticle = null },
            title = { Text(article.title) },
            text = { Text(article.description) },
            confirmButton = {
                TextButton(
                    onClick = { selectedArticle = null },
                    modifier = Modifier
                        .focusRequester(closeButtonFocusRequester)
                        .focusable()
                ) {
                    Text("Close")
                }
            }
        )
    }
}