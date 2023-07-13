package com.example.todolist.di

import android.content.Context
import com.example.todolist.App
import com.example.authorization.di.AuthorizationScreenDependencies
import com.example.edittodo.di.DetailScreenDependencies
import com.example.todolist.di.modules.CoreModule
import com.example.todolist.di.modules.NavModule
import com.example.todolist.di.modules.NetworkModule
import com.example.todolist.di.modules.PersistenceModule
import com.example.todolist.di.modules.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    PersistenceModule::class,
    RepositoryModule::class,
    CoreModule::class,
    NavModule::class
])
interface ApplicationComponent
    : DetailScreenDependencies, TodoItemsScreenDependencies, AuthorizationScreenDependencies {

    fun inject(application: App)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
