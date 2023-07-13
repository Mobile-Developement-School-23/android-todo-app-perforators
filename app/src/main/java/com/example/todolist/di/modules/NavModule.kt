package com.example.todolist.di.modules

import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.todolist.navigation.AuthorizationNavCommandProviderImpl
import com.example.todolist.navigation.TodoNavCommandProvider
import com.example.todolist.navigation.TodoNavCommandProviderImpl
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
        impl: TodoNavCommandProviderImpl
    ): TodoNavCommandProvider
}
