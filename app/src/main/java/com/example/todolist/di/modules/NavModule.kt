package com.example.todolist.di.modules

import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.todolist.navigation.AuthorizationNavCommandProviderImpl
import com.example.todolist.navigation.TodoListNavCommandProvider
import com.example.todolist.navigation.TodoListNavCommandProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface NavModule {

    @Binds
    fun bindAuthorizationNavProvider(
        impl: AuthorizationNavCommandProviderImpl
    ): AuthorizationNavCommandProvider

    @Binds
    fun bindTodoListNavProvider(
        impl: TodoListNavCommandProviderImpl
    ): TodoListNavCommandProvider
}
