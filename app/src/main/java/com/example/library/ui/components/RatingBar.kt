package com.example.library.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.library.R

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    maxRating: Int = 5
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            for (i in 1..maxRating) {
                Image(
                    painter = if (i <= rating) painterResource(R.drawable.baseline_star_24)
                        else painterResource(R.drawable.baseline_star_border_24),
                    contentDescription = "Оценка $i",
                    modifier = Modifier
                        .size(50.dp)
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onRatingChanged(i) }
                )
            }
        }
    }
}
