package com.example.library.ui.screens.images

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.library.data.local.image.toEntity
import com.example.library.ui.components.BottomDrawer
import com.example.library.ui.components.ImageBox
import com.example.library.ui.components.LoadingScreen
import com.example.library.viewmodel.ImagesViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .aspectRatio(7f / 10f)
                        ) {
                            val serializedImage = Json.encodeToString(image)
                            var isLiked by remember { mutableStateOf(image.isFavorite) }

                            Box (modifier = Modifier.fillMaxSize()) {
                                image.urls.regular?.let {
                                    ImageBox(
                                        imageLink = it,
                                        action = { navController.navigate("image_details/${image.id}") },
                                        isLiked = isLiked) { newState ->
                                        isLiked = newState
                                        viewModel.toLike(image.copy(isFavorite = isLiked).toEntity())
                                    }
                                }
                            }
                        }
                    }
                }
            }
            BottomDrawer()
        }
    }
}