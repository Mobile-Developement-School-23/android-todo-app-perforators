package com.example.todolist.di.authentication

import com.example.todolist.domain.TokenRepository

interface AuthorizationScreenDependencies {

    fun tokenRepository(): TokenRepository
}