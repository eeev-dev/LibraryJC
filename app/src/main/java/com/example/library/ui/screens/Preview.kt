package com.example.library.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.library.R
import com.example.library.ui.components.BackIconButton
import com.example.library.ui.components.RatingBar

@Composable
fun Preview(navController: NavController) {
    var isLiked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var rating by remember { mutableIntStateOf(3) }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 15.dp, end = 15.dp)
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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { navController.navigate("images_screen") }
                    ) {

                        Image(
                            painter = painterResource(R.drawable.fuji),
                            contentDescription = stringResource(R.string.place_image),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .background(
                                color = colorResource(R.color.background_grey).copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(R.string.favorites),
                                tint = if (isLiked) Color.Red else Color.White
                            )
                        }
                    }
                    BackIconButton { navController.popBackStack() }
                }
            }
            UserCard(
                name = "Evie S.",
                nickname = "evieshaffer",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                profileUrl = "https://www.lipsum.com/",
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
                    Box(Modifier.fillMaxSize()) {
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

@Composable
fun UserCard(
    name: String?,
    nickname: String?,
    description: String?,
    profileUrl: String?,
    context: Context
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.profile),
                    contentDescription = stringResource(R.string.icon),
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    if (name != null) {
                        Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Text(text = "@$nickname", color = Color.Gray, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    overflow = if (isExpanded) TextOverflow.Clip else TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Профиль автора",
                color = Color.Blue,
                modifier = Modifier.clickable {
                    // Открытие ссылки в браузере
                    if (profileUrl != null) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            )
        }
    }
}
