package com.example.data.di.modules

import android.content.Context
import com.example.data.connectivityobserver.ConnectivityObserver
import com.example.data.connectivityobserver.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule {

    @Provides
    fun provideConnectivityObserver(context: Context): ConnectivityObserver =
        NetworkConnectivityObserver(context)

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}