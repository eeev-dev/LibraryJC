package com.example.library.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.library.R
import com.example.library.ui.theme.lobsterFont
import com.example.library.ui.theme.robotoFont
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PreviewScreen(navController: NavController) {
    val colors = listOf(colorResource(R.color.light_blue), colorResource(R.color.dark_blue))

    val gradientBrush = Brush.verticalGradient(
        colors = colors
    )

    var rowWidth by remember { mutableIntStateOf(0) }

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(color = colors[0], darkIcons = false)
        systemUiController.setNavigationBarColor(color = colors[1], darkIcons = false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .clickable { navController.navigate("main_screen") }
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    rowWidth = coordinates.size.width // Запоминаем ширину Row
                }
            ) {
                Text(
                    text = "Travel",
                    color = Color.White,
                    fontSize = 44.sp,
                    fontFamily = lobsterFont,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.travel),
                    contentDescription = "icon",
                    Modifier
                        .height(36.dp)
                        .width(36.dp)
                )
            }
            Text(
                text = "Найдите место своей мечты вместе с нами",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = robotoFont,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .width(with(LocalDensity.current) { rowWidth.toDp() + 60.dp })
            )
        }
    }
}