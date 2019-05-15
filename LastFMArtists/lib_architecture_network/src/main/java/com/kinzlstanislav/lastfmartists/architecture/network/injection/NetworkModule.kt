package com.kinzlstanislav.lastfmartists.architecture.network.injection

import com.kinzlstanislav.lastfmartists.architecture.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

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
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    /*@Provides
    fun provideGithubApiService(okHttpClient: OkHttpClient): GithubApiService =
        Retrofit.Builder()
            .baseUrl(REST_GITHUB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(GithubApiService::class.java)*/
}