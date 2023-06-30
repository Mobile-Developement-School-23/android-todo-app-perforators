package com.example.todolist.di.todoitems

import com.example.todolist.di.detail.DetailScreenComponent
import com.example.todolist.di.detail.DetailScreenDependencies
import com.example.todolist.presentation.todoitems.TodoListFragment
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