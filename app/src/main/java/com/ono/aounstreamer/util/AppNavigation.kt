package com.ono.aounstreamer.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ono.aounstreamer.presentation.DetailScreen
import com.ono.aounstreamer.presentation.MainScreen
import com.ono.aounstreamer.presentation.PlayerScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = "main", modifier = modifier) {
        composable("main") {
            MainScreen { itemId ->
                navController.navigate("detail_screen/$itemId")
            }
        }

        composable("detail_screen/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            itemId?.let {
                DetailScreen(itemId) { itemUrl ->
                    navController.navigate("player/$itemUrl")
                }
            }
        }

        composable(
            route = "player/{itemUrl}",
            arguments = listOf(navArgument("itemUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemUrl = backStackEntry.arguments?.getString("itemUrl")
            itemUrl?.let {
                PlayerScreen(itemUrl)
            }
        }
    }
}
