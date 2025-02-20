package com.example.library.screens

import android.app.Activity
import android.net.Uri
import android.util.Log
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
import coil.compose.rememberImagePainter
import com.example.library.R
import com.example.library.data.model.Place
import com.example.library.viewmodel.PlaceViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson

@Composable
fun MainScreen(navController: NavController, sharedViewModel: PlaceViewModel) {
    val context = LocalContext.current

    val places by sharedViewModel.places.collectAsState()

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

    if (places.isEmpty()) Text("Загрузка")
    else MainContent(navController, places)

}

@Composable
fun MainContent(navController: NavController, places: List<Place>) {
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
            Places(navController, places)
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
fun Places(navController: NavController, places: List<Place>) {
    /*val fuji = Place(
        place = "Гора Фудзи",
        city = "Токио",
        country = "Япония",
        rating = 4.9,
        isFavorite = true,
        price = 230, // Подъем на гору бесплатный, но есть расходы на транспорт и снаряжение
        duration = 8, // В среднем восхождение занимает 6-8 часов
        temperature = -5, // На вершине даже летом температура может быть отрицательной
        description = """
        Гора Фудзи — это не только высочайшая вершина Японии (3,776 м), но и важнейший символ страны. 
        Она давно стала объектом поклонения и местом для медитации, будучи священной для японцев. 
        Её идеальная конусообразная форма привлекает туристов и фотографов со всего мира. 
        Гора является активным вулканом, последний раз извергавшимся более 300 лет назад, но её 
        присутствие в культуре Японии остаётся значимым и по сей день.
        
        Восхождение на Фудзи — это не только физический вызов, но и духовное путешествие. 
        Тысячи людей ежегодно совершают подъем, чтобы насладиться великолепием природы и увидеть 
        восход солнца с её вершины. В летний сезон открыты несколько маршрутов, и опытные альпинисты 
        могут выбрать более сложные пути. Температура на вершине, даже в жаркие летние месяцы, может 
        опускаться до отрицательных значений, поэтому важно подготовиться к холодным условиям.
        
        Для туристов, не желающих подниматься на саму вершину, доступны виды на гору с окрестных 
        холмов и смотровых площадок. Многие путешественники также выбирают путешествие по окрестным 
        районам, чтобы посетить храмы и горячие источники, которые добавляют магии и уединения в 
        это восхитительное место.
    """.trimIndent(),
        R.drawable.fuji
    )

    val andes = Place(
        place = "Анды",
        city = "Куско",
        country = "Перу",
        rating = 4.8,
        isFavorite = false,
        price = 200, // Ориентировочная стоимость похода в зависимости от маршрута
        duration = 72, // Длительность треков может варьироваться от нескольких дней до недель
        temperature = 10, // Средняя температура в горах зависит от высоты и сезона
        description = """
        Анды — одна из величайших горных цепей мира, протягивающаяся на более чем 7,000 км вдоль западного побережья Южной Америки. 
        Здесь сосредоточены одни из самых захватывающих и труднодоступных ландшафтов на планете, которые привлекают тысячи туристов 
        и альпинистов каждый год. Анды являются родиной древней цивилизации инков и оставили после себя впечатляющие памятники и руины, 
        включая знаменитую крепость Мачу-Пикчу, расположенную на одном из вершинных хребтов.
        
        Этот горный регион также известен своими уникальными экосистемами, от туманных лесов в низинах до заснеженных вершин. 
        В Андах можно встретить разнообразие флоры и фауны, включая редких животных, таких как ламы, альпаки и викуньи. 
        Популярные маршруты для треккинга, такие как Инкский трек, предлагают путешественникам возможность погрузиться в природу, 
        исследуя древние руины и наслаждаясь потрясающими видами.
        
        Температура в горах может сильно колебаться в зависимости от высоты и времени года. В зимний период температуры могут опускаться 
        ниже нуля на вершинах, в то время как в низинах может быть достаточно тепло. Треккинг и альпинизм в Андах — это незабываемое 
        приключение, которое требует хорошей физической подготовки и уважения к силам природы.
    """.trimIndent(),
        R.drawable.andy
    )*/

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)  // Расстояние между элементами
    ) {
        items(places) { place ->
            PlaceItem(place, navController)  // Отображаем PlaceItem для каждого элемента списка
        }
    }
}

@Composable
fun PlaceItem(place: Place, navController: NavController) {
    val deepLinkUri = Uri.parse("android-app://androidx.navigation/details_screen/${place.id}")
    val painter = rememberImagePainter(
        data = place.imageLink,  // Здесь у нас URL картинки
        builder = {
            crossfade(true)  // Плавное появление при загрузке
        }
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
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop  // Масштабирование изображения
            )

            // Иконка "Добавить в избранное"
            var isFavorite by remember { mutableStateOf(place.isFavorite) }

            FavoriteIconButton(isFavorite = isFavorite) {
                isFavorite = !isFavorite
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
fun FavoriteIconButton(isFavorite: Boolean, onClick: () -> Unit) {
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
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isFavorite) Color.Red else Color.White // Цвет иконки
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