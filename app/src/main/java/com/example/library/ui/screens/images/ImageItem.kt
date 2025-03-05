package com.example.library.ui.screens.images

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.library.R
import com.example.library.data.network.UnsplashImage
import com.example.library.ui.components.FavoriteIconButton
import com.example.library.viewmodel.ImagesViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ImageItem(
    image: UnsplashImage,
    navController: NavController,
    viewModel: ImagesViewModel
) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(
                data = image.urls.regular
            ).apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build()
        )

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .aspectRatio(7f / 10f)
    ) {
        val serializedImage = Json.encodeToString(image)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate("image_details/${Uri.encode(serializedImage)}") } ) {

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.place_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            var isLiked by remember { mutableStateOf(false) }

            FavoriteIconButton(isLiked) {
                isLiked = !isLiked
            }
        }
    }
}