package com.ono.aounstreamer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import com.ono.aounstreamer.util.AppNavigation
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()


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

}

class TestGetMediaItemsUseCase : GetMediaItemsUseCase {
    override suspend fun invoke(query: String): Flow<PagingData<MediaItem>> {
        return flow {
            emit(PagingData.empty()) // or provide mock data here
        }
    }
}

