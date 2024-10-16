package com.obidia.movieapp.data.di

import com.obidia.movieapp.data.remote.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiServiceModule {

    @Provides
    fun provideAPi(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}