package com.ono.aounstreamer

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.ono.aounstreamer.util.AppNavigation
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        // Mock the MainViewModel
        viewModel = mockk(relaxed = true)
    }

    @Composable
    @Test
    fun navigateFromMainScreenToDetailScreen() {
        // Arrange: set up NavController and Composable
        val navController = rememberNavController()
        composeTestRule.setContent {
            AppNavigation(viewModel = viewModel, navController = navController)
        }

        // Act: Click on the item to trigger navigation
        composeTestRule.onNodeWithText("Main Screen Button").performClick()

        // Assert: Verify that the detail screen is displayed
        composeTestRule.onNodeWithText("Detail Screen Title").assertExists() // Replace with actual title text on DetailScreen
        verify { viewModel.selectedItem = any() }
    }

    @Composable
    @Test
    fun navigateFromDetailScreenToPosterScreen() {
        // Arrange
        val navController = rememberNavController()
        val testPosterUrl = "http://example.com/testPoster.jpg"
        viewModel.selectedItem = mockk(relaxed = true) // Mock a selected item
        composeTestRule.setContent {
            AppNavigation(viewModel = viewModel, navController = navController)
        }

        // Act: Click "Watch Poster" button on DetailScreen
        composeTestRule.onNodeWithText("Watch Poster").performClick()

        // Assert: Verify that PosterScreen is displayed
        composeTestRule.onNodeWithText("Poster Screen Title").assertExists() // Replace with actual title text on PosterScreen
        verify { viewModel.selectedItem = any() }
    }

    @Composable
    @Test
    fun navigateFromDetailScreenToPlayerScreen() {
        // Arrange
        val navController = rememberNavController()
        viewModel.selectedItem = mockk(relaxed = true)
        composeTestRule.setContent {
            AppNavigation(viewModel = viewModel, navController = navController)
        }

        // Act: Click "Watch Video" button on DetailScreen
        composeTestRule.onNodeWithText("Watch Video").performClick()

        // Assert: Verify that PlayerScreen is displayed
        composeTestRule.onNodeWithText("Player Screen Title").assertExists() // Replace with actual title text on PlayerScreen
    }
}
