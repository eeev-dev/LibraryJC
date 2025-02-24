package com.example.library.screens

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.library.R
import com.example.library.data.local.toEntity
import com.example.library.data.model.Place
import com.example.library.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)
) {
    val context = LocalContext.current

    val places by mainViewModel.places.collectAsState()

    // Перехватываем кнопку "Назад"
    BackHandler {
        // Выход из приложения
        (context as? Activity)?.finishAffinity()
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.White,
            darkIcons = true
        )
    }

    if (places.isEmpty()) LoadingScreen()
    else MainContent(navController, mainViewModel, places)
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()  // Круговой индикатор загрузки
    }
}

@Composable
fun MainContent(navController: NavController, mainViewModel: MainViewModel, places: List<Place>) {
    val poppinsFamily = FontFamily(Font(R.font.poppins))
    Box(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 22.dp, start = 15.dp, end = 15.dp)
    ) {
        Column {
            Head()
            SearchBar()
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Популярные места",
                fontFamily = poppinsFamily,
                fontSize = 20.sp,
                color = colorResource(R.color.dark_grey)
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalScrollView()
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)  // Расстояние между элементами
            ) {
                items(places) { place ->
                    PlaceItem(place = place, mainViewModel = mainViewModel, navController = navController)  // Отображаем PlaceItem для каждого элемента списка
                }
            }
            BottomDrawer()
        }
    }
}

@Composable
fun Head() {
    val interFont = FontFamily(
        Font(R.font.inter, FontWeight.Normal)
    )

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
                    text = "Привет, Диана \uD83D\uDC4B",
                    fontFamily = FontFamily(Font(R.font.montserrat_bold, FontWeight.Bold)),
                    fontSize = 28.sp,
                    color = colorResource(R.color.dark_grey)
                )
                Text(
                    text = "Исследуйте мир",
                    fontFamily = interFont,
                    fontSize = 20.sp,
                    color = colorResource(R.color.grey)
                )
            }
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.avatar),
                contentDescription = "icon",
                Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
        }
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                1.5.dp,
                colorResource(R.color.light_grey),
                RoundedCornerShape(20.dp)
            ) // Граница с закругленными углами
            .padding(start = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp), // Отступ для иконки
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Поле для ввода запроса (без границы, только текст)
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .weight(4f)
                    .padding(end = 8.dp), // Отступ от палочки
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        if (query.isEmpty()) {
                            Text("Search...", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            Row(Modifier.weight(1f)) {
                // Разделительная палочка
                Box(
                    modifier = Modifier
                        .width(1.5.dp)
                        .height(40.dp)
                        .background(colorResource(R.color.light_grey))
                )

                // Иконка фильтра
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { /* Обработчик нажатия на фильтр */ }
                )
            }
        }
    }
}

@Composable
fun HorizontalScrollView() {
    // Состояние, которое отслеживает активную кнопку
    var selectedButton by remember { mutableStateOf(0) }

    // Создаем состояние прокрутки
    val scrollState = rememberScrollState()

    // Оборачиваем Row в horizontalScroll
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState), // Включаем горизонтальную прокрутку
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Кнопки
        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 0,
            onClick = { selectedButton = 0 },
            text = "Самые просматриваемые"
        )

        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 1,
            onClick = { selectedButton = 1 },
            text = "Рядом"
        )

        ButtonItem(
            selectedButton = selectedButton,
            buttonIndex = 2,
            onClick = { selectedButton = 2 },
            text = "Последние"
        )
    }
}

@Composable
fun ButtonItem(
    selectedButton: Int,
    buttonIndex: Int,
    onClick: () -> Unit,
    text: String
) {
    val robotoFont = FontFamily(
        Font(R.font.roboto, FontWeight.Normal)
    )
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedButton == buttonIndex) Color(0xFF2F2F2F) else Color.Transparent,
            contentColor = if (selectedButton == buttonIndex) Color.White else Color(0xFFC5C5C5)
        )
    ) {
        Text(
            text = text,
            fontFamily = robotoFont,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PlaceItem(place: Place, navController: NavController, mainViewModel: MainViewModel) {
    val deepLinkUri = Uri.parse("android-app://androidx.navigation/details_screen/${place.id}")
    val painter =
        rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(
            data = place.imageLink  // Здесь у нас URL картинки
        ).apply(block = fun ImageRequest.Builder.() {
            crossfade(true)  // Плавное появление при загрузке
        }).build()
        )
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp), // Тень
        modifier = Modifier
            .size(250.dp, 350.dp)
            .padding(horizontal = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate(deepLinkUri) }) { // Оборачиваем в Box для позиционирования

            // Фото места
            Image(
                painter = painter,
                contentDescription = "Изображение места",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop  // Масштабирование изображения
            )

            var isLiked by remember { mutableStateOf(place.isFavorite) }

            FavoriteIconButton(isLiked) {
                isLiked = !isLiked
                mainViewModel.toLike(place.toEntity().copy(isFavorite = isLiked))
            }

            val robotoFont = FontFamily(Font(R.font.roboto, FontWeight.Normal))

            // Бокс, который должен быть прижат к низу
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Растягиваем по ширине
                    .align(Alignment.BottomCenter) // Прижимаем к низу и центру
                    .padding(16.dp) // Отступы внутри бокса
                    .background(
                        Color.Black.copy(alpha = 0.5f), // Прозрачный фон (40%)
                        shape = RoundedCornerShape(16.dp) // Скругленные углы
                    )
            ) {
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
                            contentDescription = "Местоположение",
                            tint = colorResource(R.color.pale_grey) // Цвет иконки
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
                            contentDescription = "Избранное",
                            tint = colorResource(R.color.pale_grey) // Цвет иконки
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
    }
}

@Composable
fun FavoriteIconButton(isLiked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Размер фона
                .background(
                    color = colorResource(R.color.background_grey).copy(alpha = 0.4f), // Цвет фона
                    shape = CircleShape // Круглая форма
                )
                .padding(8.dp) // Отступ внутри фона
                .align(Alignment.TopEnd)
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isLiked) Color.Red else Color.White // Цвет иконки
                )
            }
        }
    }
}

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