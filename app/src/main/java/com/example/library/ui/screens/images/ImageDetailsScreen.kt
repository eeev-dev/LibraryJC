package com.example.library.ui.screens.images

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.library.data.local.toEntity
import com.example.library.data.model.UnsplashImage
import com.example.library.ui.components.BackIconButton
import com.example.library.ui.components.ImageBox
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ImageDetailsScreen(
    navController: NavController,
    image: UnsplashImage
) {
    var isLiked by remember { mutableStateOf(false) }
    Column {
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
                        onLikeClick = { }
                    )
                }
                BackIconButton(navController)
            }
        }
        image.user?.name?.let { Text(text = it) }
        image.user?.name?.let { Text(text = it) }
        image.description?.let { Text(text = it) }
        FlowRow(
            modifier = Modifier.background(Color.Red).padding(8.dp),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            image.tags?.forEach { tag ->
                Text(
                    text = "#$tag",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}