package com.ono.aounstreamer

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow

/*@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    fun provideViewModel(): MainViewModel {
        val queryFlow = MutableStateFlow("")
        return mockk<MainViewModel> {
            every { _query } returns queryFlow
        }
    }
}*/
