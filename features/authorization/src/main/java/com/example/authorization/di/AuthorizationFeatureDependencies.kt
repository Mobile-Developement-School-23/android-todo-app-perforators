package com.example.authorization.di

import android.content.Context
import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.authorization_api.TokenRepository
import kotlin.properties.Delegates.notNull

interface AuthorizationFeatureDependencies {

    fun tokenRepository(): TokenRepository

    fun context(): Context

    fun authNavCommandProvider(): AuthorizationNavCommandProvider
}

interface AuthorizationFeatureDepsProvider {
    val deps: AuthorizationFeatureDependencies

    companion object : AuthorizationFeatureDepsProvider by AuthorizationFeatureDepsStore
}

object AuthorizationFeatureDepsStore : AuthorizationFeatureDepsProvider {

    override var deps: AuthorizationFeatureDependencies by notNull()
}