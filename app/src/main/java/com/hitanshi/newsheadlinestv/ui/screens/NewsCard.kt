package com.hitanshi.newsheadlinestv.ui.screens

import androidx.compose.animation.animateContentSize
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
    focusRequester: FocusRequester? = null
) {

    var expanded by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }
    var pressStart by remember { mutableStateOf(0L) }

    val scale by animateFloatAsState(
        targetValue = if (focused) 1.05f else 1f,
        label = "cardScale"
    )

    // --- BUG FIX -------------------------------------------------------
    // The card previously used `.fillMaxHeight()`, which locks the card
    // to the full height of the parent LazyRow *before* content is even
    // measured. That means when `expanded` flips to true and maxLines
    // changes, there is no room for the card to grow into, so
    // animateContentSize() has nothing to animate and the extra text
    // just gets clipped — making it look like "Read more" does nothing.
    //
    // Fix: let the card wrap its own content within a min/max height
    // range instead of stretching to fill the row. Combined with
    // `verticalAlignment = Alignment.Top` on the LazyRow (see
    // NewsScreen.kt), the card can now actually expand downward.
    // ---------------------------------------------------------------------
    var baseModifier = Modifier
        .width(620.dp)
        .heightIn(min = 560.dp, max = 900.dp)
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
            containerColor = Color(0xFF1A1A1A)
        ),

        // White border when focused (was Color.Green)
        border = BorderStroke(
            if (focused) 3.dp else 1.dp,
            if (focused) Color.White else Color.DarkGray
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
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.height(16.dp))

            // Source + time badge row, matching the reference UI
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
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = article.description,
                color = Color.LightGray,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = if (expanded) "▲ Collapse" else "▼ Read more",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}