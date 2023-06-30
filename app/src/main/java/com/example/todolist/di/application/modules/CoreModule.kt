package com.example.todolist.di.application.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoreModule {

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}