package com.example.hauiwaterquality.di

import com.example.hauiwaterquality.data.api.RemoteServices
import com.example.hauiwaterquality.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitHelper(client: OkHttpClient): Retrofit {
        GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(
                CoroutineCallAdapterFactory()
            ).client(client).build()
    }

    @Singleton
    @Provides
    fun provideCreateRetrofitHelper(retrofit: Retrofit): RemoteServices =
        retrofit.create(RemoteServices::class.java)

    @Singleton
    @Provides
    fun provideBaseRetrofitHelper(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .writeTimeout(6 * 1000.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(6 * 1000.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
        return builder.build()
    }

}
