package com.example.todolist.di.application

import android.content.Context
import com.example.todolist.App
import com.example.todolist.di.application.modules.CoreModule
import com.example.todolist.di.application.modules.NetworkModule
import com.example.todolist.di.application.modules.PersistenceModule
import com.example.todolist.di.application.modules.RepositoryModule
import com.example.todolist.di.authentication.AuthorizationScreenDependencies
import com.example.todolist.di.detail.DetailScreenDependencies
import com.example.todolist.di.todoitems.TodoItemsScreenDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    PersistenceModule::class,
    RepositoryModule::class,
    CoreModule::class
])
interface ApplicationComponent
    : DetailScreenDependencies, TodoItemsScreenDependencies, AuthorizationScreenDependencies {

    fun inject(application: App)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}