package com.ono.aounstreamer.util

import android.util.Log
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

val TAG = "AppNavigation"

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Detail : Screen("detail_screen")
    data object Player : Screen("player")
}


@Composable
fun AppNavigation(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {


    NavHost(navController, startDestination = Screen.Main.route, modifier = modifier) {
        composable(Screen.Main.route) {
            MainScreen(viewModel) { item ->
                viewModel.selectedItem = item
                navigateToDetailScreen(navController)
            }
        }

        composable(Screen.Detail.route) {
            viewModel.selectedItem?.let {
                DetailScreen(viewModel) { itemUrl ->
                    navigateToPlayerScreen(navController, itemUrl)
                }
            }
        }

        composable(
            route = Screen.Player.route + "/{itemUrl}",
            arguments = listOf(navArgument("itemUrl") {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val itemUrl = backStackEntry.arguments?.getString("itemUrl")
            PlayerScreen()
        }
    }
}

private fun navigateToDetailScreen(navController: NavHostController) {
    navController.navigate(Screen.Detail.route)
}

private fun navigateToPlayerScreen(navController: NavHostController, itemUrl: String?) {
    val safeItemUrl = itemUrl?.takeIf { it.isNotEmpty() } ?: "/default_url"
    navController.navigate(Screen.Player.route + safeItemUrl)
}


