package com.example.library.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.library.R

@Composable
fun BottomDrawer() {
    // Стейт для отслеживания выбранной иконки
    var selectedIconIndex by remember { mutableStateOf(0) }

    // Список иконок
    val icons = listOf(
        Icons.Rounded.Home,
        Icons.Rounded.FavoriteBorder,
        Icons.Rounded.Face
    )

    // Контейнер, который занимает весь экран
    Box(modifier = Modifier.fillMaxSize()) {
        // Ваш BottomDrawer прижат к низу
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp), // Расстояние между иконками
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter) // Прикрепляем к низу экрана
        ) {
            icons.forEachIndexed { index, icon ->
                // Оборачиваем каждый элемент в Column с весом
                Column(
                    modifier = Modifier
                        .weight(1f) // Распределение пространства
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            selectedIconIndex = index
                        } // При клике меняем выбранную иконку
                        .padding(horizontal = 8.dp), // Добавляем немного отступа для эстетики
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon $index",
                        modifier = Modifier.size(35.dp), // Размер иконки
                        tint = if (selectedIconIndex == index) colorResource(R.color.dark_grey) else colorResource(
                            R.color.icon_grey
                        )
                    )
                    // Если иконка выбрана, рисуем красный кружок
                    if (selectedIconIndex == index) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, CircleShape)
                        )
                    }
                }
            }
        }
    }
}