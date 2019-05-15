package com.kinzlstanislav.lastfmartists.architecture.core.usecase

import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope

abstract class BaseCoroutineUseCase(appCoroutineScope: AppCoroutineScope): BaseUseCase(),
    AppCoroutineScope by appCoroutineScope