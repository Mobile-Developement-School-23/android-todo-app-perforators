package com.example.authorization_api

interface TokenRepository {

    suspend fun addDefaultToken()

    suspend fun addYandexToken(token: String)
}