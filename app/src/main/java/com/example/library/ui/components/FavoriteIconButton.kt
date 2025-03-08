package com.example.library.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.library.R
import com.example.library.ui.extensions.clickableWithoutRipple

@Composable
fun FavoriteIconButton(
    isLiked: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = colorResource(R.color.background_grey).copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .padding(8.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.favorites),
                tint = if (isLiked) Color.Red else Color.White,
                modifier = Modifier.clickableWithoutRipple(
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick
                )
            )
        }
    }
}