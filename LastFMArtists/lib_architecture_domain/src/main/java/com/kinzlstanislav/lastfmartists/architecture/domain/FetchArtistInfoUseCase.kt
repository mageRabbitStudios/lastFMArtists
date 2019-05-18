package com.kinzlstanislav.lastfmartists.architecture.domain

import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.extension.isConnectionError
import com.kinzlstanislav.lastfmartists.architecture.core.extension.throwIfCancellationException
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.core.usecase.BaseCoroutineUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result.GenericError
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result.NetworkError
import com.kinzlstanislav.lastfmartists.architecture.repository.LastfmArtistRepository
import javax.inject.Inject

class FetchArtistInfoUseCase @Inject constructor(
    appCoroutineScope: AppCoroutineScope,
    private val repository: LastfmArtistRepository
) : BaseCoroutineUseCase(appCoroutineScope) {

    suspend fun execute(id: String) = backgroundTask {
        try {
            val response = repository.getArtistInfo(id)
            Result.Success(response)
        } catch(exception: Exception) {
            exception.throwIfCancellationException()
            if (exception.isConnectionError()) NetworkError else GenericError
        }
    }

    sealed class Result {
        data class Success(val artistInfo: ArtistInfo) : Result()
        object NetworkError : Result()
        object GenericError : Result()
    }

}