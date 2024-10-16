package com.obidia.movieapp.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(
                okHttpClient.newBuilder().addInterceptor(
                    ChuckerInterceptor.Builder(context)
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                ).build()
            )
            baseUrl("https://api.themoviedb.org/3/")
        }.build()
    }

    @Provides
    fun provideOkHttp(requestInterceptor: RequestInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(requestInterceptor)
        }.build()

    @Provides
    fun provideRequestInterceptor(): RequestInterceptor = RequestInterceptor()
}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newReq = chain.request().newBuilder().addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkM2Y1NTlmYzgxYTY0NzAzOGY0YjE2OTI0NWM2NWRkYSIsIm5iZiI6MTcyNTA4Mzk3NC40MDE0MTUsInN1YiI6IjYyNjFjMzViZDQ2NTM3MDA0ZjYzNmRkZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Oi_9KkUQRJ6LGYMZOeBL3Ez65-pGbRpZj8LaUTWJjHs"
        ).build()

        return chain.proceed(newReq)
    }
}