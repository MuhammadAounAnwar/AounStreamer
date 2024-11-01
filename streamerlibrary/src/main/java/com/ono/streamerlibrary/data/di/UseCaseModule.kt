package com.ono.streamerlibrary.data.di

import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCaseImpl
import com.ono.streamerlibrary.domain.repository.MediaRepository
import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideMediaSearchUseCase(mediaRepository: MediaRepository): GetMediaItemsUseCase {
        return GetMediaItemsUseCaseImpl(mediaRepository)
    }

}