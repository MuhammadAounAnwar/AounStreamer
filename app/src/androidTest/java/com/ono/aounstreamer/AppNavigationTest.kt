package com.ono.aounstreamer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.ono.aounstreamer.util.AppNavigation
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

//    private val viewModel = mockk<MainViewModel>(relaxed = true)

    private val testUseCase = TestGetMediaItemsUseCase()
    private val viewModel = MainViewModel(testUseCase)


    private val mediaItem = MediaItem(
        posterPath = "/samplePoster.jpg",
        mediaType = "movie",
        title = "Sample Movie",
        overview = "This is a sample overview."
    )


    @Test
    fun appStartsAtMainScreen() = runTest {
        composeTestRule.setContent {
            AppNavigation(navController = rememberNavController(), viewModel = viewModel)
        }
        composeTestRule.onNodeWithContentDescription("SearchBar").assertIsDisplayed()
    }


    @Test
    fun clickingItemNavigatesToDetailScreen() = runTest {
        val testUseCase = object : GetMediaItemsUseCase {
            override suspend fun invoke(query: String): Flow<PagingData<MediaItem>> {
                val mediaItemList = PagingData.from(listOf(mediaItem))
                return flow { emit(mediaItemList) }
            }
        }

        val viewModel = MainViewModel(testUseCase)

        composeTestRule.setContent {
            AppNavigation(navController = rememberNavController(), viewModel = viewModel)
        }

        composeTestRule.onNodeWithContentDescription("MediaCard")
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("MediaCard")
            .performClick()

        composeTestRule.onNodeWithContentDescription("Item Title")
            .assertIsDisplayed()
    }

    @Test
    fun detailScreenNavigatesToPosterScreen() {
        every { viewModel.selectedItem } returns mediaItem

        composeTestRule.setContent {
            val navController = rememberNavController()
            AppNavigation(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Watch Poster").performClick()

        composeTestRule.onNodeWithText("Poster Screen Title")
            .assertIsDisplayed()
    }

    @Test
    fun detailScreenNavigatesToPlayerScreen() {
        every { viewModel.selectedItem } returns mediaItem

        composeTestRule.setContent {
            val navController = rememberNavController()
            AppNavigation(navController = navController, viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("Watch Video").performClick()

        composeTestRule.onNodeWithText("Player Screen Title")
            .assertIsDisplayed()
    }
}

class TestGetMediaItemsUseCase : GetMediaItemsUseCase {
    override suspend fun invoke(query: String): Flow<PagingData<MediaItem>> {
        return flow {
            emit(PagingData.empty()) // or provide mock data here
        }
    }
}

