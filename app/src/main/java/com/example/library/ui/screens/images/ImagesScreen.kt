package com.example.library.ui.screens.images

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.library.ui.components.BottomDrawer
import com.example.library.ui.components.LoadingScreen
import com.example.library.ui.screens.home.MainContent
import com.example.library.ui.screens.home.PlaceItem
import com.example.library.viewmodel.ImagesViewModel

@Composable
fun ImagesScreen(
    navController: NavController,
    viewModel: ImagesViewModel = viewModel(factory = ImagesViewModel.factory)
) {
    val context = LocalContext.current

    val images by viewModel.images.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 22.dp, start = 15.dp, end = 15.dp)
    ) {
        Column {
            if (images.isEmpty()) LoadingScreen()
            else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(images) { image ->
                        ImageItem(image, navController, viewModel)
                    }
                }
            }
            BottomDrawer()
        }
    }
}