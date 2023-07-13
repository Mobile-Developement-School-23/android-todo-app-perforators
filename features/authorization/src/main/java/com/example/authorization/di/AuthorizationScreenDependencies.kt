package com.example.authorization.di

import android.content.Context
import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.authorization_api.TokenRepository
import kotlin.properties.Delegates.notNull

interface AuthorizationScreenDependencies {

    fun tokenRepository(): TokenRepository

    fun context(): Context

    fun authNavCommandProvider(): AuthorizationNavCommandProvider
}

interface AuthorizationDepsProvider {
    val deps: AuthorizationScreenDependencies

    companion object : AuthorizationDepsProvider by AuthorizationDepsStore
}

object AuthorizationDepsStore : AuthorizationDepsProvider {

    override var deps: AuthorizationScreenDependencies by notNull()
}