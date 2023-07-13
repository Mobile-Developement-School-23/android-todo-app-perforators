package com.example.di

import com.example.edittodo.DetailFragment
import com.example.todolist.TodoListFragment
import dagger.Component

@TodoFeatureScope
@Component(
    dependencies = [TodoFeatureDependencies::class]
)
interface TodoFeatureComponent {

    fun inject(fragment: TodoListFragment)

    fun inject(fragment: DetailFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: TodoFeatureDependencies): TodoFeatureComponent
    }
}