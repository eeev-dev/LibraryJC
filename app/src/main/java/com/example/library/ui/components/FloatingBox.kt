package com.example.library.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.library.R
import com.example.library.data.model.Place
import com.example.library.ui.theme.interFont
import com.example.library.ui.theme.robotoFont

@Composable
fun FloatingBoxInMain(
    place: Place,
    modifier: Modifier
) {
    Box(modifier) {
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
                    contentDescription = stringResource(R.string.location),
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
                    contentDescription = stringResource(R.string.favorites),
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

@Composable
fun FloatingBoxInDetails(
    place: Place,
    modifier: Modifier
) {
    Box(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = place.place,
                    fontFamily = interFont,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Row {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = stringResource(R.string.location),
                        tint = colorResource(R.color.pale_grey)
                    )
                    Text(
                        text = place.country,
                        fontFamily = robotoFont,
                        fontSize = 18.sp,
                        color = colorResource(R.color.pale_grey)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 16.dp)
                    .weight(2f)
            ) {
                Text(
                    text = stringResource(R.string.price),
                    color = colorResource(R.color.pale_grey),
                    fontFamily = robotoFont,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    text = "$${place.price}",
                    color = colorResource(R.color.pale_grey),
                    fontFamily = robotoFont,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}