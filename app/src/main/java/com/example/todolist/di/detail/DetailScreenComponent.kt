package com.example.todolist.di.detail

import com.example.todolist.presentation.detail.DetailFragment
import dagger.Component

@DetailScreenScope
@Component(
    dependencies = [DetailScreenDependencies::class]
)
interface DetailScreenComponent {

    fun inject(fragment: DetailFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: DetailScreenDependencies): DetailScreenComponent
    }
}