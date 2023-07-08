package com.example.data.di.modules

import android.content.Context
import com.example.data.workers.Worker
import com.example.data.workers.network.NetworkAvailableWorker
import com.example.data.workers.network.connectivityobserver.ConnectivityObserver
import com.example.data.workers.network.connectivityobserver.NetworkConnectivityObserver
import com.example.data.workers.periodic.PeriodicWorker
import com.example.domain.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule {

    @IntoSet
    @Provides
    fun providePeriodicWorker(context: Context): Worker = PeriodicWorker(context)

    @IntoSet
    @Provides
    fun provideNetworkAvailableWorker(
        connectivityObserver: ConnectivityObserver,
        repository: TodoItemsRepository,
        coroutineDispatcher: CoroutineDispatcher
    ): Worker = NetworkAvailableWorker(
        connectivityObserver, repository, coroutineDispatcher
    )

    @Provides
    fun provideConnectivityObserver(context: Context): ConnectivityObserver =
        NetworkConnectivityObserver(context)

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}