package com.obidia.movieapp.data.di

import com.obidia.movieapp.data.repository.RepositoryImplementation
import com.obidia.movieapp.domain.repo.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [ApiServiceModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repositoryImplementation: RepositoryImplementation): Repository
}