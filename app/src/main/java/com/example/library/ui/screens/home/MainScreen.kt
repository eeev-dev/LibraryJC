package com.example.library.ui.screens.home

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.library.R
import com.example.library.data.local.toEntity
import com.example.library.data.model.Place
import com.example.library.ui.components.BottomDrawer
import com.example.library.ui.components.FloatingBoxInMain
import com.example.library.ui.components.ImageBox
import com.example.library.ui.components.LoadingScreen
import com.example.library.ui.components.SearchBar
import com.example.library.ui.theme.poppinsFamily
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
    else Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 22.dp, start = 15.dp, end = 15.dp)
    ) { MainContent(navController, mainViewModel, places) }
}

@Composable
fun MainContent(
    navController: NavController,
    mainViewModel: MainViewModel,
    places: List<Place>
) {
    Box(
        Modifier.fillMaxSize()
    ) {
        Column {
            Head(navController)
            SearchBar()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.popular_places),
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
                    var isLiked by remember { mutableStateOf(place.isFavorite) }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        modifier = Modifier
                            .size(250.dp, 350.dp)
                            .padding(horizontal = 10.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            ImageBox(
                                imageLink = place.imageLink,
                                navigate = {
                                    navController
                                        .navigate(
                                            Uri.parse("android-app://androidx.navigation/details_screen/${place.id}")
                                        )
                                },
                                isLiked = isLiked) { newState ->
                                isLiked = newState
                                mainViewModel.toLike(
                                    place.toEntity().copy(isFavorite = newState)
                                )
                            }
                            FloatingBoxInMain(
                                place,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                                    .background(
                                        Color.Black.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                        }
                    }
                    /*PlaceItem(
                        place = place,
                        mainViewModel = mainViewModel,
                        navController = navController
                    )*/
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            BottomDrawer()
        }
    }
}