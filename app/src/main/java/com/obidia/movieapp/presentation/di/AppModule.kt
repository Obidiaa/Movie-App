package com.obidia.movieapp.presentation.di

import com.obidia.movieapp.domain.usecase.UseCase
import com.obidia.movieapp.domain.usecase.UseCaseImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideUseCase(useCaseImplementation: UseCaseImplementation): UseCase
}