package com.hitanshi.newsheadlinestv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hitanshi.newsheadlinestv.data.repository.NewsRepository
import com.hitanshi.newsheadlinestv.ui.screens.NewsScreen
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModel
import com.hitanshi.newsheadlinestv.viewmodel.NewsViewModelFactory

@Composable
fun NewsNavGraph(
    repository: NewsRepository
) {

    val vm: NewsViewModel = viewModel(
        factory = NewsViewModelFactory(repository)
    )

    NewsScreen(vm)

}