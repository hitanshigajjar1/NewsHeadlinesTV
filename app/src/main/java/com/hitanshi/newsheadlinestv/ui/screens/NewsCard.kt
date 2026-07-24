package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.hitanshi.newsheadlinestv.data.model.Article

@Composable
fun NewsCard(
    article: Article
) {

    var expanded by remember {

        mutableStateOf(false)

    }

    var focused by remember {

        mutableStateOf(false)

    }

    Card(

        modifier = Modifier

            .fillMaxWidth()

            .padding(12.dp)

            .onFocusChanged {

                focused = it.isFocused

            }

//            .focusable()

            .clickable {

                expanded = !expanded

            }
            .focusable(),

        border = BorderStroke(

            if (focused) 3.dp else 1.dp,

            if (focused) Color.Red else Color.Gray

        )

    ) {

        Column {

            AsyncImage(

                model = article.imageUrl,

                contentDescription = null,

                modifier = Modifier

                    .fillMaxWidth()

                    .height(240.dp)

            )

            Text(

                article.title,

                fontSize = 26.sp,

                fontWeight = FontWeight.Bold,

                modifier = Modifier.padding(16.dp)

            )

            Text(

                article.description,

                maxLines = if (expanded) Int.MAX_VALUE else 3,

                overflow = TextOverflow.Ellipsis,

                modifier = Modifier.padding(16.dp)

            )

        }

    }

}