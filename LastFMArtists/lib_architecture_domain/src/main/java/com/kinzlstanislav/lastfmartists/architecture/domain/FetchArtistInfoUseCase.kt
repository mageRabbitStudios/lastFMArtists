package com.kinzlstanislav.lastfmartists.architecture.domain

import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.extension.isConnectionError
import com.kinzlstanislav.lastfmartists.architecture.core.extension.throwIfCancellationException
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.core.usecase.BaseCoroutineUseCase
import com.kinzlstanislav.lastfmartists.architecture.repository.LastfmRepository
import javax.inject.Inject

class FetchArtistInfoUseCase @Inject constructor(
    appCoroutineScope: AppCoroutineScope,
    private val repository: LastfmRepository
) : BaseCoroutineUseCase(appCoroutineScope) {

    suspend fun executeWithMbid(mbid: String) = backgroundTask {
        try {
            val response = repository.getArtistInfo(mbid)
            Result.Success(response)
        } catch(exception: Exception) {
            exception.throwIfCancellationException()
            if (exception.isConnectionError()) Result.NetworkError else Result.GenericError
        }
    }

    sealed class Result {
        data class Success(val artistInfo: ArtistInfo) : Result()
        object NetworkError : Result()
        object GenericError : Result()
    }

}