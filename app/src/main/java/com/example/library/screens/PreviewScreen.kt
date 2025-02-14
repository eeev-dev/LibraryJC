package com.example.library.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.library.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PreviewScreen(navController: NavController) {
    val colors = listOf(Color(0xFF0172B2), Color(0xFF001645))

    val gradientBrush = Brush.verticalGradient(
        colors = colors
    )

    val lobsterFont = FontFamily(
        Font(R.font.lobster, FontWeight.Normal)
    )

    val robotoFont = FontFamily(
        Font(R.font.roboto, FontWeight.Normal)
    )

    var rowWidth by remember { mutableIntStateOf(0) }

    val systemUiController = rememberSystemUiController()

    /*// Устанавливаем цвета панели навигации и статус-бара
    SideEffect {
        systemUiController.setStatusBarColor(
            color = colors[0], // Верхний цвет градиента
            darkIcons = false // false, если нужен белый текст в статус-баре
        )
        systemUiController.setNavigationBarColor(
            color = colors[1], // Нижний цвет градиента
            darkIcons = false // false, если иконки должны быть белыми
        )
    }*/

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
                    Modifier.height(36.dp).width(36.dp)
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

@Preview
@Composable
fun PreviewScreen() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0172B2), Color(0xFF001645))
    )

    val lobsterFont = FontFamily(
        Font(R.font.lobster, FontWeight.Normal)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Row (modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "Travel",
                color = Color.White,
                fontSize = 44.sp,
                fontFamily = lobsterFont
            )
            Image(bitmap = ImageBitmap.imageResource(R.drawable.travel), contentDescription = "icon")
        }
    }
}