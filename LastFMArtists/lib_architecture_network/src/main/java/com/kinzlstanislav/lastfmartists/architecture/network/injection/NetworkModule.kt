package com.kinzlstanislav.lastfmartists.architecture.network.injection

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kinzlstanislav.lastfmartists.architecture.BuildConfig
import com.kinzlstanislav.lastfmartists.architecture.network.api.LastfmApiService
import com.kinzlstanislav.lastfmartists.architecture.network.api.LastfmRestData
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return okHttpBuilder.build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideLastfmApiService(okHttpClient: OkHttpClient): LastfmApiService =
            Retrofit.Builder()
                .baseUrl(LastfmRestData.LASTFM_API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(LastfmApiService::class.java)
}