package com.example.authorization.di

import android.content.Context
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import dagger.Module
import dagger.Provides

@Module
class AuthorizationModule {

    @Provides
    @AuthorizationFeatureScope
    fun provideYandexSdk(
        context: Context,
        options: YandexAuthOptions
    ) = YandexAuthSdk(context, options)

    @Provides
    fun provideYandexOptions(context: Context) = YandexAuthOptions(context)
}