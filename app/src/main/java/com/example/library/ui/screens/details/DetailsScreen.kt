package com.example.library.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun DetailsScreen(
    navController: NavController,
    id: Int,
    detailsViewModel: DetailsViewModel = viewModel(factory = DetailsViewModel.factory)
) {
    detailsViewModel.getPlace(id)
    val place = detailsViewModel.place

    if (place != null) {
        Main(navController, place, detailsViewModel)
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun Main(
    navController: NavController,
    place: Place,
    viewModel: DetailsViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ImageHead(place, navController, viewModel)
            Tabs()
            AdditionalInfo(place)
            Text(
                text = place.description,
                modifier = Modifier.padding(top = 15.dp, bottom = 50.dp),
                fontFamily = robotoFont,
                fontSize = 18.sp,
                color = Color(0xFFA5A5A5)
            )
        }

        Button(
            onClick = { },
            modifier = Modifier
                .padding(12.dp)
                .height(50.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B1B1B),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(4f),
                    text = stringResource(R.string.book_now),
                    fontFamily = robotoFont,
                    fontSize = 20.sp
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.AutoMirrored.Rounded.Send,
                    contentDescription = stringResource(R.string.icon),
                    tint = Color.White
                )
            }
        }
    }
}