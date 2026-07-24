package com.hitanshi.newsheadlinestv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hitanshi.newsheadlinestv.ui.navigation.NewsNavGraph
import com.hitanshi.newsheadlinestv.ui.theme.NewsHeadlinesTVTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val repository =
            (application as NewsApplication).repository

        setContent {

            NewsHeadlinesTVTheme {

                NewsNavGraph(repository)

            }

        }

    }

}