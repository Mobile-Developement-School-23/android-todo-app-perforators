package com.example.todolist.di.application.modules

import android.content.Context
import com.example.todolist.data.connectivityobserver.ConnectivityObserver
import com.example.todolist.data.connectivityobserver.NetworkConnectivityObserver
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