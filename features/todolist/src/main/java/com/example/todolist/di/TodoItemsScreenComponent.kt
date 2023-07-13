package com.example.todolist.di

import com.example.todolist.TodoListFragment
import dagger.Component

@TodoItemsScreenScope
@Component(
    dependencies = [TodoItemsScreenDependencies::class]
)
interface TodoItemsScreenComponent {

    fun inject(fragment: TodoListFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: TodoItemsScreenDependencies): TodoItemsScreenComponent
    }
}