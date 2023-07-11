package com.example.authorization.di

import com.example.authorization.AuthorizationFragment
import dagger.Component

@AuthorizationScreenScope
@Component(
    modules = [AuthorizationModule::class],
    dependencies = [AuthorizationScreenDependencies::class]
)
interface AuthorizationScreenComponent {

    fun inject(fragment: AuthorizationFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AuthorizationScreenDependencies): AuthorizationScreenComponent
    }
}