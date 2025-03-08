package com.example.library.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.library.R

@Composable
fun ImageBox(
    imageLink: String,
    action: () -> Unit = { },
    isLiked: Boolean,
    onLikeClick: (Boolean) -> Unit
) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(
                data = imageLink
            ).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { action() }
    ) {

        Image(
            painter = painter,
            contentDescription = stringResource(R.string.place_image),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        FavoriteIconButton(isLiked) {
            onLikeClick(!isLiked)
        }
    }
}