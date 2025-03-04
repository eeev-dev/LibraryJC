package com.example.library.ui.screens.image_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.library.data.network.UnsplashImage
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ImageDetailsScreen(
    navController: NavController,
    image: UnsplashImage
) {
    Column {
        image.user?.name?.let { Text(text = it) }
        image.user?.name?.let { Text(text = it) }
        image.description?.let { Text(text = it) }
        FlowRow(
            modifier = Modifier.padding(8.dp),
            mainAxisSpacing = 8.dp,  // Пробел между элементами по горизонтали
            crossAxisSpacing = 8.dp  // Пробел между элементами по вертикали
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