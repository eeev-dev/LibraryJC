package com.example.library.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library.R
import com.example.library.data.model.Place
import com.example.library.ui.theme.robotoFont

@Composable
fun AdditionalInfo(
    place: Place
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.recent_actions), "${place.duration} ${stringResource(R.string.hour)}")
        }
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.cloud), "${place.temperature} °C")
        }
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.star), place.rating.toString())
        }
    }
}

@Composable
fun IconText(
    icon: ImageBitmap,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(0xFFEDEDED),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp)
        ) {
            Image(
                bitmap = icon,
                contentDescription = stringResource(R.string.icon),
                Modifier
                    .height(50.dp)
                    .width(50.dp),
                colorFilter = ColorFilter.tint(Color(0xFF3F3F3F))
            )
        }
        Text(
            text = text,
            color = Color(0xFF7E7E7E),
            fontSize = 18.sp,
            fontFamily = robotoFont,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}