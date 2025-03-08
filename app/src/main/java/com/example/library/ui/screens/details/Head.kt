package com.example.library.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.library.data.local.place.toEntity
import com.example.library.data.model.Place
import com.example.library.ui.components.BackIconButton
import com.example.library.ui.components.FloatingBoxInDetails
import com.example.library.ui.components.ImageBox
import com.example.library.viewmodel.DetailsViewModel

@Composable
fun ImageHead(
    place: Place,
    navController: NavController,
    viewModel: DetailsViewModel
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            var isLiked by remember { mutableStateOf(place.isFavorite) }

            ImageBox(
                imageLink = place.imageLink,
                isLiked = isLiked,
                onLikeClick = { newState ->
                    isLiked = newState
                    viewModel.toLike(
                        place.toEntity().copy(isFavorite = newState)
                    )
                }
            )

            BackIconButton { navController.popBackStack() }

            FloatingBoxInDetails(
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
}