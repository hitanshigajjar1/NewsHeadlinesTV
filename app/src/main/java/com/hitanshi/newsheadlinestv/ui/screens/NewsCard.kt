package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hitanshi.newsheadlinestv.data.model.Article

@Composable
fun NewsCard(
    article: Article,
    onRefresh: () -> Unit,
    focusRequester: FocusRequester? = null
) {

    var expanded by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }
    var pressStart by remember { mutableStateOf(0L) }

    val scale by animateFloatAsState(
        targetValue = if (focused) 1.05f else 1f,
        label = "cardScale"
    )

    var baseModifier = Modifier
        .width(620.dp)
        .fillMaxHeight()
        .scale(scale)

    // Attach the FocusRequester (only the first card gets one) BEFORE
    // .focusable(), so requestFocus() actually targets this node.
    if (focusRequester != null) {
        baseModifier = baseModifier.focusRequester(focusRequester)
    }

    Card(
        modifier = baseModifier
            .onFocusChanged { focusState ->
                focused = focusState.isFocused
            }
            .focusable()
            .onPreviewKeyEvent { event ->

                when (event.key) {

                    Key.DirectionCenter,
                    Key.Enter,
                    Key.NumPadEnter -> {
                        if (event.type == KeyEventType.KeyUp) {
                            expanded = !expanded
                        }
                        true
                    }

                    Key.DirectionDown -> {
                        when (event.type) {
                            KeyEventType.KeyDown -> {
                                if (pressStart == 0L) {
                                    pressStart = System.currentTimeMillis()
                                }
                                true
                            }

                            KeyEventType.KeyUp -> {
                                val duration = System.currentTimeMillis() - pressStart
                                pressStart = 0L
                                if (duration > 700) {
                                    onRefresh()
                                }
                                true
                            }

                            else -> false
                        }
                    }

                    else -> false
                }
            },

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1F1F1F)
        ),

        border = BorderStroke(
            if (focused) 4.dp else 1.dp,
            if (focused) Color.Green else Color.DarkGray
        )

    ) {

        Column(
            Modifier
                .padding(20.dp)
                .animateContentSize()
        ) {

            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = article.title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "${article.source}   ${article.publishedAt}",
                color = Color.Green,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(18.dp))

            Text(
                text = article.description,
                color = Color.LightGray,
                fontSize = 20.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = if (expanded) "▲ Press OK to Collapse" else "▼ Press OK to Read More",
                color = Color.Yellow,
                fontWeight = FontWeight.Bold
            )
        }
    }
}