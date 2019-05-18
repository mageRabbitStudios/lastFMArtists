package com.kinzlstanislav.lastfmartists.architecture.domain

import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.extension.isConnectionError
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.core.usecase.BaseCoroutineUseCase
import com.kinzlstanislav.lastfmartists.architecture.repository.LastfmArtistRepository
import java.lang.Exception
import java.util.concurrent.CancellationException
import javax.inject.Inject

class FetchLastfmArtistsByNameUseCase @Inject constructor(
    appCoroutineScope: AppCoroutineScope,
    private val lastfmRepository: LastfmArtistRepository
) : BaseCoroutineUseCase(appCoroutineScope) {

    suspend fun execute(artistName: String, limit: Int) = backgroundTask {
        try {
            val response = lastfmRepository.getArtistsSearchResult(artistName, limit)
            Result.Success(response)
        } catch(exception: Exception) {
            if (exception is CancellationException) throw exception
            if (exception.isConnectionError()) Result.NetworkError else Result.GenericError
        }
    }

    sealed class Result {
        data class Success(val artists: List<Artist>) : Result()
        object NetworkError : Result()
        object GenericError : Result()
    }

}