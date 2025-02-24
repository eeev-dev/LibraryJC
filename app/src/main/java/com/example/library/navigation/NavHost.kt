package com.example.library.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.example.library.screens.DetailsScreen
import com.example.library.screens.MainScreen
import com.example.library.screens.PreviewScreen
import com.example.library.viewmodel.DetailsViewModel
import com.example.library.viewmodel.MainViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "preview_screen") {
        composable("preview_screen") { PreviewScreen(navController) }
        composable("main_screen") { MainScreen(navController) }
        composable(
            "details_screen/{id}",
            deepLinks = listOf(navDeepLink { uriPattern = "android-app://androidx.navigation/details_screen/{id}" })
        ) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id") ?: ""
            val id = idString.toIntOrNull() ?: 0
            DetailsScreen(navController, id)
        }
    }
}
