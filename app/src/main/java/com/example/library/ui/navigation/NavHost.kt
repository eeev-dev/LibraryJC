package com.example.library.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.library.ui.screens.Preview
import com.example.library.ui.screens.PreviewScreen
import com.example.library.ui.screens.details.DetailsScreen
import com.example.library.ui.screens.home.MainScreen
import com.example.library.ui.screens.images.ImageDetailsScreen
import com.example.library.ui.screens.images.ImagesScreen

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("preview_screen") { PreviewScreen(navController) }
        composable("main_screen") { MainScreen(navController) }
        composable("preview") { Preview(navController) }
        composable("images_screen") { ImagesScreen(navController) }
        composable("details_screen/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            DetailsScreen(navController, id)
        }
        composable("image_details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ImageDetailsScreen(navController, id)
        }
    }
}
