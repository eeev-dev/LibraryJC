package com.example.library.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.library.R
import com.example.library.ui.theme.interFont
import com.example.library.ui.theme.montserratFamily

@Composable
fun Head(navController: NavController) {
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
                    text = stringResource(R.string.greeting),
                    fontFamily = montserratFamily,
                    fontSize = 28.sp,
                    color = colorResource(R.color.dark_grey)
                )
                Text(
                    modifier = Modifier.clickable { navController.navigate("images_screen") },
                    text = stringResource(R.string.explore_the_world),
                    fontFamily = interFont,
                    fontSize = 20.sp,
                    color = colorResource(R.color.grey),
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.avatar),
                contentDescription = stringResource(R.string.icon),
                Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
        }
    }
}