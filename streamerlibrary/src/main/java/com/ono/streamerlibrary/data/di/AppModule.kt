package com.ono.streamerlibrary.data.di

import com.ono.streamerlibrary.data.remote.ApiService
import com.ono.streamerlibrary.data.repository.MediaRepositoryImpl
import com.ono.streamerlibrary.domain.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMediaRepository(
        apiService: ApiService
    ): MediaRepository =
        MediaRepositoryImpl(apiService)
}