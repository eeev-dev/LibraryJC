package com.example.library.ui.screens.main_screen

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.library.R
import com.example.library.data.local.toEntity
import com.example.library.data.model.Place
import com.example.library.ui.components.BottomDrawer
import com.example.library.ui.components.FavoriteIconButton
import com.example.library.ui.theme.interFont
import com.example.library.ui.theme.montserratFamily
import com.example.library.ui.theme.poppinsFamily
import com.example.library.ui.theme.robotoFont
import com.example.library.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    val context = LocalContext.current

    val places by mainViewModel.places.collectAsState()

    BackHandler {
        (context as? Activity)?.finishAffinity()
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }

    if (places.isEmpty()) LoadingScreen()
    else MainContent(navController, mainViewModel, places)
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MainContent(
    navController: NavController,
    mainViewModel: MainViewModel,
    places: List<Place>
) {
    Box(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 22.dp, start = 15.dp, end = 15.dp)
    ) {
        Column {
            Head()
            SearchBar()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Популярные места",
                fontFamily = poppinsFamily,
                fontSize = 20.sp,
                color = colorResource(R.color.dark_grey)
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalScrollView()
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(places) { place ->
                    PlaceItem(place = place, mainViewModel = mainViewModel, navController = navController)
                }
            }
            BottomDrawer()
        }
    }
}

@Composable
fun Head() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.padding(end = 8.dp)) {
                Text(
                    text = "Привет, Диана \uD83D\uDC4B",
                    fontFamily = montserratFamily,
                    fontSize = 28.sp,
                    color = colorResource(R.color.dark_grey)
                )
                Text(
                    text = "Исследуйте мир",
                    fontFamily = interFont,
                    fontSize = 20.sp,
                    color = colorResource(R.color.grey)
                )
            }
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.avatar),
                contentDescription = "icon",
                Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
        }
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                1.5.dp,
                colorResource(R.color.light_grey),
                RoundedCornerShape(20.dp)
            )
            .padding(start = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .weight(4f)
                    .padding(end = 8.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        if (query.isEmpty()) {
                            Text("Search...", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Row(Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .width(1.5.dp)
                        .height(40.dp)
                        .background(colorResource(R.color.light_grey))
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { /* Обработчик нажатия на фильтр */ }
                )
            }
        }
    }
}

@Composable
fun HorizontalScrollView() {
    var selectedButton by remember { mutableIntStateOf(0) }

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 0,
            onClick = { selectedButton = 0 },
            text = "Самые просматриваемые"
        )

        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 1,
            onClick = { selectedButton = 1 },
            text = "Рядом"
        )

        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 2,
            onClick = { selectedButton = 2 },
            text = "Последние"
        )
    }
}

@Composable
fun ButtonItem(
    selectedButton: Int,
    buttonIndex: Int,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedButton == buttonIndex) Color(0xFF2F2F2F) else Color.Transparent,
            contentColor = if (selectedButton == buttonIndex) Color.White else Color(0xFFC5C5C5)
        )
    ) {
        Text(
            text = text,
            fontFamily = robotoFont,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PlaceItem(
    place: Place,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val deepLinkUri = Uri.parse("android-app://androidx.navigation/details_screen/${place.id}")
    val painter =
        rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(
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
                contentDescription = "Изображение места",
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
                            contentDescription = "Местоположение",
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
                            contentDescription = "Избранное",
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