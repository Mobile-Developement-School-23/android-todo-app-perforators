package com.example.todolist.domain

interface TokenRepository {

    suspend fun addDefaultToken()

    suspend fun addYandexToken(token: String)
}