package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hitanshi.newsheadlinestv.data.model.Article

@Composable
fun NewsCard(
    article: Article
) {

    var expanded by remember {

        mutableStateOf(false)

    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            Modifier.padding(16.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(article.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                article.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = article.description,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                modifier = Modifier.clickable {

                    expanded = !expanded

                }
            )

            Spacer(Modifier.height(8.dp))

            Text(article.source)

        }

    }

}