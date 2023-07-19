package com.example.authorization.di

import com.example.authorization.AuthorizationFragment
import dagger.Component

@AuthorizationFeatureScope
@Component(
    modules = [AuthorizationModule::class],
    dependencies = [AuthorizationFeatureDependencies::class]
)
interface AuthorizationFeatureComponent {

    fun inject(fragment: AuthorizationFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: AuthorizationFeatureDependencies): AuthorizationFeatureComponent
    }
}