package com.example.todolist.di.application.modules

import com.example.todolist.data.repositories.TodoItemsRepositoryImpl
import com.example.todolist.data.local.sources.TodoLocalDataSource
import com.example.todolist.data.local.sources.TodoLocalDataSourceImpl
import com.example.todolist.data.remote.TodoRemoteDataSource
import com.example.todolist.data.remote.TodoRemoteDataSourceImpl
import com.example.todolist.data.repositories.TokenRepositoryImpl
import com.example.todolist.data.repositories.synchronizer.LastWriteWinsSynchronizer
import com.example.todolist.data.repositories.synchronizer.Synchronizer
import com.example.todolist.domain.TodoItemsRepository
import com.example.todolist.domain.TokenRepository
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