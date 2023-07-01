package com.example.todolist.di.authentication

import com.example.todolist.presentation.authorization.AuthorizationFragment
import dagger.Component

@AuthorizationScreenScope
@Component(
    dependencies = [AuthorizationScreenDependencies::class]
)
interface AuthorizationScreenComponent {

    fun inject(fragment: AuthorizationFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AuthorizationScreenDependencies): AuthorizationScreenComponent
    }
}