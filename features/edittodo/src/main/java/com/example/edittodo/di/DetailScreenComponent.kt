package com.example.edittodo.di

import dagger.Component

@DetailScreenScope
@Component(
    dependencies = [DetailScreenDependencies::class]
)
interface DetailScreenComponent {

    fun inject(fragment: com.example.edittodo.DetailFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: DetailScreenDependencies): DetailScreenComponent
    }
}