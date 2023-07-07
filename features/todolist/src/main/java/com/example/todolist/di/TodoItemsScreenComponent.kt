package com.example.todolist.di

import dagger.Component

@TodoItemsScreenScope
@Component(
    dependencies = [TodoItemsScreenDependencies::class]
)
interface TodoItemsScreenComponent {

    fun inject(fragment: com.example.todolist.TodoListFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: TodoItemsScreenDependencies): TodoItemsScreenComponent
    }
}