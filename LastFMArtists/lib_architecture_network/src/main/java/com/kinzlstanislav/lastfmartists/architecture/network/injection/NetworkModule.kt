package com.kinzlstanislav.lastfmartists.architecture.network.injection

import dagger.Module

@Module
class NetworkModule {

    /*@Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        }
        return okHttpBuilder.build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    fun provideGithubApiService(okHttpClient: OkHttpClient): GithubApiService =
        Retrofit.Builder()
            .baseUrl(REST_GITHUB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
            .create(GithubApiService::class.java)*/
}