package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onArticleClick: () -> Unit,
    focusRequester: FocusRequester? = null
) {

    var focused by remember { mutableStateOf(false) }
    var pressStart by remember { mutableStateOf(0L) }

    val scale by animateFloatAsState(
        targetValue = if (focused) 1.05f else 1f,
        label = "cardScale"
    )

    // Card no longer expands in place. Selecting OK/Enter now opens a full
    // summary in an AlertDialog (see NewsScreen.kt) instead of growing the
    // card inline, which was fighting the LazyRow's height constraint.
    var baseModifier = Modifier
        .width(620.dp)
        .heightIn(min = 560.dp, max = 620.dp)
        .scale(scale)

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

                    // Select/OK now opens the AlertDialog with the full
                    // summary instead of toggling a local expanded state.
                    Key.DirectionCenter,
                    Key.Enter,
                    Key.NumPadEnter -> {
                        if (event.type == KeyEventType.KeyUp) {
                            onArticleClick()
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
            containerColor = Color(0xFF1A1A1A)
        ),

        border = BorderStroke(
            if (focused) 3.dp else 1.dp,
            if (focused) Color.White else Color.DarkGray
        )

    ) {

        Column(
            Modifier.padding(20.dp)
        ) {

            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF223A5E), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = article.source,
                        color = Color(0xFF6FB2FF),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "•  ${article.publishedAt}",
                    color = Color.Gray,
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = article.title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = article.description,
                color = Color.LightGray,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "▶ Press OK for full story",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}