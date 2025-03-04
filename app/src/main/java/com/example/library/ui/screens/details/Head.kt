package com.example.library.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                contentDescription = stringResource(R.string.place_image),
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
                                contentDescription = stringResource(R.string.location),
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
                            text = stringResource(R.string.price),
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