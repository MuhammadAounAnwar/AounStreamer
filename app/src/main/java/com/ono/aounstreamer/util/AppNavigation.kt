package com.ono.aounstreamer.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ono.aounstreamer.MainViewModel
import com.ono.aounstreamer.presentation.DetailScreen
import com.ono.aounstreamer.presentation.MainScreen
import com.ono.aounstreamer.presentation.PlayerScreen
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.model.toJsonObject
import com.ono.streamerlibrary.domain.model.toMediaItem

@Composable
fun AppNavigation(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier
) {


    NavHost(navController, startDestination = "main", modifier = modifier) {
        composable("main") {
            MainScreen(viewModel) { item ->
                viewModel.selectedItem = item
                navController.navigate("detail_screen")
            }
        }

        composable("detail_screen") {
            viewModel.selectedItem?.let {
                DetailScreen(viewModel = viewModel) { itemUrl ->
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
                PlayerScreen()
            }
        }
    }
}
