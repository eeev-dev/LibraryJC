package com.example.library.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
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
import com.example.library.ui.components.FavoriteIconButton
import com.example.library.ui.theme.robotoFont
import com.example.library.viewmodel.MainViewModel

@Composable
fun PlaceItem(
    place: Place,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val deepLinkUri = Uri.parse("android-app://androidx.navigation/details_screen/${place.id}")
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(
            data = place.imageLink
        ).apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
        }).build()
        )
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .size(250.dp, 350.dp)
            .padding(horizontal = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(deepLinkUri) }) {

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.place_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            var isLiked by remember { mutableStateOf(place.isFavorite) }

            FavoriteIconButton(isLiked) {
                isLiked = !isLiked
                mainViewModel.toLike(place.toEntity().copy(isFavorite = isLiked))
            }

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
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "${place.place}, ${place.city}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = robotoFont
                    )
                    Spacer(
                        modifier = Modifier
                            .height(15.dp)
                            .fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = stringResource(R.string.location),
                            tint = colorResource(R.color.pale_grey)
                        )
                        Text(
                            text = "${place.city}, ${place.country}",
                            color = colorResource(R.color.pale_grey),
                            fontSize = 12.sp,
                            fontFamily = robotoFont,
                            modifier = Modifier.weight(3f)
                        )
                        Icon(
                            modifier = Modifier.weight(1f),
                            imageVector = Icons.Rounded.Star,
                            contentDescription = stringResource(R.string.favorites),
                            tint = colorResource(R.color.pale_grey)
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = place.rating.toString(),
                            color = colorResource(R.color.pale_grey),
                            fontSize = 12.sp,
                            fontFamily = robotoFont
                        )
                    }
                }
            }
        }
    }
}