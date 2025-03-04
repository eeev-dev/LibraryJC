package com.example.library.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.library.data.network.UnsplashImage
import com.example.library.ui.screens.details.DetailsScreen
import com.example.library.ui.screens.home.MainScreen
import com.example.library.ui.screens.PreviewScreen
import com.example.library.ui.screens.image_details.ImageDetailsScreen
import com.example.library.ui.screens.images.ImagesScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "preview_screen") {
        composable("preview_screen") { PreviewScreen(navController) }
        composable("main_screen") { MainScreen(navController) }
        composable("images_screen") { ImagesScreen(navController) }
        composable(
            "details_screen/{id}",
            deepLinks = listOf(navDeepLink { uriPattern = "android-app://androidx.navigation/details_screen/{id}" })
        ) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id") ?: ""
            val id = idString.toIntOrNull() ?: 0
            DetailsScreen(navController, id)
        }
        composable("image_details/{imageData}") { backStackEntry ->
            val imageData = backStackEntry.arguments?.getString("imageData")

            if (imageData != null) {
                val unsplashImage = Json.decodeFromString<UnsplashImage>(imageData)
                ImageDetailsScreen(navController, unsplashImage)
            }
        }
    }
}
