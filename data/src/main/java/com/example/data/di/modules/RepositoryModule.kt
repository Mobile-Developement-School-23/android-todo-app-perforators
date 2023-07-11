package com.example.data.di.modules

import com.example.data.local.sources.TodoLocalDataSource
import com.example.data.local.sources.TodoLocalDataSourceImpl
import com.example.data.remote.TodoRemoteDataSource
import com.example.data.remote.TodoRemoteDataSourceImpl
import com.example.data.repositories.TodoItemsRepositoryImpl
import com.example.data.repositories.TokenRepositoryImpl
import com.example.data.repositories.synchronizer.LastWriteWinsSynchronizer
import com.example.data.repositories.synchronizer.Synchronizer
import com.example.domain.TodoItemsRepository
import com.example.domain.TokenRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindTodoRepository(impl: TodoItemsRepositoryImpl): TodoItemsRepository

    @Binds
    @Singleton
    fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @Binds
    fun bindTodoLocalDataSource(impl: TodoLocalDataSourceImpl): TodoLocalDataSource

    @Binds
    fun bindTodoRemoteDataSource(impl: TodoRemoteDataSourceImpl): TodoRemoteDataSource

    @Binds
    fun bindSynchronizer(impl: LastWriteWinsSynchronizer): Synchronizer
}