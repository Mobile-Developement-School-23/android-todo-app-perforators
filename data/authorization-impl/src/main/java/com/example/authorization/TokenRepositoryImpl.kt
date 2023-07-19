package com.example.authorization

import com.example.authorization_api.TokenRepository
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