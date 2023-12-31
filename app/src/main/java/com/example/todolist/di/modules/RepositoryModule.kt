package com.example.todolist.di.modules

import com.example.todo.local.sources.TodoLocalDataSource
import com.example.todo.local.sources.TodoLocalDataSourceImpl
import com.example.todo.remote.TodoRemoteDataSource
import com.example.todo.remote.TodoRemoteDataSourceImpl
import com.example.todo.TodoItemsRepositoryImpl
import com.example.authorization.TokenRepositoryImpl
import com.example.todo.synchronizer.LastWriteWinsSynchronizer
import com.example.todo.synchronizer.Synchronizer
import com.example.todo_api.TodoItemsRepository
import com.example.authorization_api.TokenRepository
import com.example.notification.ChangeListener
import com.example.settings_api.SettingsRepository
import com.example.settings_impl.SettingsRepositoryImpl
import com.example.todo_api.OnChangeTodoListListener
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
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
    @Singleton
    fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    @Binds
    fun bindTodoLocalDataSource(impl: TodoLocalDataSourceImpl): TodoLocalDataSource

    @Binds
    fun bindTodoRemoteDataSource(impl: TodoRemoteDataSourceImpl): TodoRemoteDataSource

    @Binds
    fun bindSynchronizer(impl: LastWriteWinsSynchronizer): Synchronizer

    @IntoSet
    @Binds
    fun bindOnChangeListener(impl: ChangeListener): OnChangeTodoListListener
}