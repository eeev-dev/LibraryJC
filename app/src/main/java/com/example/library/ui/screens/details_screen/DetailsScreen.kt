package com.example.library.ui.screens.details_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.library.R
import com.example.library.data.local.toEntity
import com.example.library.data.model.Place
import com.example.library.ui.components.BackIconButton
import com.example.library.ui.components.FavoriteIconButton
import com.example.library.ui.theme.interFont
import com.example.library.ui.theme.robotoFont
import com.example.library.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    id: Int,
    detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.factory)
) {
    detailsViewModel.getPlace(id)
    val place = detailsViewModel.place

    if (place != null) {
        Main(navController, place, detailsViewModel)
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun Main(
    navController: NavController,
    place: Place,
    viewModel: DetailsViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ImageHead(place, navController, viewModel)
            HorizontalScroll()
            AdditionalInfo(place)
            Text(
                text = place.description,
                modifier = Modifier.padding(top = 15.dp, bottom = 50.dp),
                fontFamily = robotoFont,
                fontSize = 18.sp,
                color = Color(0xFFA5A5A5)
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(12.dp)
                .height(50.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B1B1B),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(4f),
                    text = "Забронировать сейчас",
                    fontFamily = robotoFont,
                    fontSize = 20.sp
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = "Забронировать",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ImageHead(
    place: Place,
    navController: NavController,
    viewModel: DetailsViewModel
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = place.imageLink)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }
        ).build()
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = "Изображение места",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            var isLiked by remember { mutableStateOf(place.isFavorite) }

            FavoriteIconButton(isLiked) {
                isLiked = !isLiked
                viewModel.toLike(place.toEntity().copy(isFavorite = isLiked))
            }

            BackIconButton(navController)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    ) {
                        Text(
                            text = place.place,
                            fontFamily = interFont,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                        Row {
                            Icon(
                                imageVector = Icons.Rounded.LocationOn,
                                contentDescription = "Местоположение",
                                tint = colorResource(R.color.pale_grey)
                            )
                            Text(
                                text = place.country,
                                fontFamily = robotoFont,
                                fontSize = 18.sp,
                                color = colorResource(R.color.pale_grey)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 16.dp)
                            .weight(2f)
                    ) {
                        Text(
                            text = "Цена",
                            color = colorResource(R.color.pale_grey),
                            fontFamily = robotoFont,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                        Text(
                            text = "$${place.price}",
                            color = colorResource(R.color.pale_grey),
                            fontFamily = robotoFont,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalScroll() {
    var selectedButton by remember { mutableIntStateOf(0) }

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            selectedButton = selectedButton,
            buttonIndex = 0,
            onClick = { selectedButton = 0 },
            text = "Обзор"
        )

        Button(
            selectedButton = selectedButton,
            buttonIndex = 1,
            onClick = { selectedButton = 1 },
            text = "Подробности"
        )
    }
}

@Composable
fun Button(
    selectedButton: Int,
    buttonIndex: Int,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = if (selectedButton == buttonIndex) colorResource(R.color.dark_grey) else colorResource(
                R.color.light_grey
            ),
        )
    ) {
        Text(
            text = text,
            fontFamily = interFont,
            fontSize = returnSp(selectedButton == buttonIndex).sp
        )
    }
}

fun returnSp(selectedButton: Boolean): Int {
    return if (selectedButton) 22
    else 16
}

@Composable
fun AdditionalInfo(
    place: Place
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.recent_actions), "${place.duration} ч")
        }
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.cloud), "${place.temperature} °C")
        }
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.star), place.rating.toString())
        }
    }
}

@Composable
fun IconText(
    icon: ImageBitmap,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(0xFFEDEDED),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp)
        ) {
            Image(
                bitmap = icon,
                contentDescription = "icon",
                Modifier
                    .height(50.dp)
                    .width(50.dp),
                colorFilter = ColorFilter.tint(Color(0xFF3F3F3F))
            )
        }
        Text(
            text = text,
            color = Color(0xFF7E7E7E),
            fontSize = 18.sp,
            fontFamily = robotoFont,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}