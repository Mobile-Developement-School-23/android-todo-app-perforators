package com.example.domain

interface TokenRepository {

    suspend fun addDefaultToken()

    suspend fun addYandexToken(token: String)
}