package com.example.data.repositories

import com.example.data.local.sources.TokenLocalDataSource
import com.example.domain.TokenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : TokenRepository {

    override suspend fun addDefaultToken() = withContext(dispatcher) {
        tokenLocalDataSource.change(TokenLocalDataSource.DEFAULT_TOKEN)
    }

    override suspend fun addYandexToken(token: String) = withContext(dispatcher) {
        tokenLocalDataSource.change("$OAUTH $token")
    }

    companion object {
        private const val OAUTH = "OAuth"
    }
}