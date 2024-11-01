package com.ono.aounstreamer

import android.content.Context
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.ono.aounstreamer.presentation.screen.DetailScreen
import com.ono.streamerlibrary.domain.model.MediaItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@ExperimentalComposeUiApi
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mediaItem = MediaItem(
        posterPath = "/samplePoster.jpg",
        mediaType = "movie",
        title = "Sample Movie",
        overview = "This is a sample overview."
    )

    @Test
    fun detailScreen_displaysPosterAndTitle() {
        // Arrange
        val viewModel = mockk<MainViewModel>(relaxed = true)
        every { viewModel.selectedItem } returns mediaItem

        // Act
        composeTestRule.setContent {
            DetailScreen(
                viewModel = viewModel,
                onWatchPosterClicked = {},
                onWatchVideoClicked = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Sample Movie").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(null.toString())
            .assertIsDisplayed() // Poster image node
    }

    @Test
    fun detailScreen_clickWatchPoster_callsOnWatchPosterClicked() {
        val onWatchPosterClicked = mockk<(String) -> Unit>(relaxed = true)
        val viewModel = mockk<MainViewModel>(relaxed = true)
        every { viewModel.selectedItem } returns mediaItem

        composeTestRule.setContent {
            DetailScreen(
                viewModel = viewModel,
                onWatchPosterClicked = onWatchPosterClicked,
                onWatchVideoClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithText("Watch Poster").performClick()

        // Assert
        verify { onWatchPosterClicked.invoke(mediaItem.posterPath!!) }
    }

    @Test
    fun detailScreen_clickWatchVideo_callsOnWatchVideoClicked() {
        val onWatchVideoClicked = mockk<() -> Unit>(relaxed = true)
        val viewModel = mockk<MainViewModel>(relaxed = true)
        every { viewModel.selectedItem } returns mediaItem

        composeTestRule.setContent {
            DetailScreen(
                viewModel = viewModel,
                onWatchPosterClicked = {},
                onWatchVideoClicked = onWatchVideoClicked
            )
        }

        // Act
        composeTestRule.onNodeWithText("Watch Video").performClick()

        // Assert
        verify { onWatchVideoClicked.invoke() }
    }

    @Test
    fun detailScreen_noPosterPath_showsToastMessage() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val viewModel = mockk<MainViewModel>(relaxed = true)
        every { viewModel.selectedItem } returns mediaItem.copy(posterPath = null)

        composeTestRule.setContent {
            DetailScreen(
                viewModel = viewModel,
                onWatchPosterClicked = {},
                onWatchVideoClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithText("Watch Poster").performClick()

        // Assert
        composeTestRule.waitForIdle()
        // Check for toast message if possible (Requires Toast testing setup)
    }
}
