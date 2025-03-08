package com.example.library.ui.screens.images

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.library.R
import com.example.library.data.local.image.toEntity
import com.example.library.data.model.UnsplashImage
import com.example.library.ui.components.AuthorCard
import com.example.library.ui.components.BackIconButton
import com.example.library.ui.components.ImageBox
import com.example.library.ui.components.LoadingScreen
import com.example.library.ui.components.RatingBar
import com.example.library.viewmodel.ImageViewModel

@Composable
fun ImageDetailsScreen(
    navController: NavController,
    id: String,
    viewModel: ImageViewModel = viewModel(factory = ImageViewModel.factory)
) {
    viewModel.getImage(id)
    val image = viewModel.image
    if (image != null) {
        Content(
            image,
            navController,
            viewModel
        )
    }
    else LoadingScreen()
}

@Composable
fun Content(
    image: UnsplashImage,
    navController: NavController,
    viewModel: ImageViewModel
) {
    var isLiked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var rating by remember { mutableIntStateOf(image.rating) }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 15.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    image.urls.regular?.let {
                        ImageBox(
                            imageLink = it,
                            isLiked = isLiked,
                            action = {
                                if (image.urls.raw != null) {
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(image.urls.raw))
                                    startActivity(context, intent, null)
                                } else Toast.makeText(context, "Нет ссылки", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            onLikeClick = { newState ->
                                isLiked = newState
                                viewModel.toLike(image.copy(isFavorite = isLiked).toEntity())
                            }
                        )
                    }
                    BackIconButton { navController.popBackStack() }
                }
            }
            AuthorCard(
                name = image.user?.name,
                nickname = image.user?.username,
                description = image.description,
                profileUrl = image.user?.portfolio_url,
                context
            )
            Row(modifier = Modifier
                .height(80.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(4f)
                        .padding(top = 8.dp, end = 8.dp)
                ) {
                    RatingBar(
                        rating = rating,
                        onRatingChanged = { newRating ->
                            rating = newRating
                            viewModel.toRate(image.copy(rating = rating).toEntity())
                        }
                    )
                }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(top = 8.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clickable {
                                shareData(
                                    context,
                                    image.description,
                                    rating,
                                    image.urls.raw ?: ""
                                )
                            }) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = stringResource(R.string.share),
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                            tint = Color(0xFF006df0)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}

fun shareData(context: Context, description: String?, rating: Int, url: String) {
    val shareText = buildString {
        append("Вам может понравиться это место! \uD83C\uDF0D✈\uFE0F\n")
        append("Описание: $description\n")
        append(if (url == "") "Изображение: $url\n" else "")
        append("Я оцениваю на $rating/5\n")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    val chooser = Intent.createChooser(intent, "Поделиться с...")
    ContextCompat.startActivity(context, chooser, null)
}