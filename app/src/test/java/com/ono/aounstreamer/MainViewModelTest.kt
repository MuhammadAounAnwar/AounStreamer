package com.ono.aounstreamer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private var searchMediaUseCase: GetMediaItemsUseCase = mockk()

    @RelaxedMockK
    private lateinit var pagingData: PagingData<MediaItem>

    @Inject
    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = MainViewModel(searchMediaUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search updates query and emits mediaItems`() = runTest {
        val query = "test query"
        coEvery { searchMediaUseCase(query) } returns flowOf(pagingData)

        viewModel.search(query)

        advanceUntilIdle()

        coVerify { searchMediaUseCase(query) }
    }

    @Test
    fun `selectedItem is initially null`() {
        Assert.assertNull(viewModel.selectedItem)
    }

    @Test
    fun `selectedItem can be updated`() {
        val mediaItem = MediaItem(id = 1, title = "Test Media Item")
        viewModel.selectedItem = mediaItem
        assertEquals(mediaItem, viewModel.selectedItem)
    }
}