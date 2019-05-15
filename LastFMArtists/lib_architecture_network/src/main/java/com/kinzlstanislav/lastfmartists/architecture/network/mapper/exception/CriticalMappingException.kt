package com.kinzlstanislav.lastfmartists.architecture.network.mapper.exception

import java.io.IOException

class CriticalMappingException(message: String = "No message") : IOException(message)