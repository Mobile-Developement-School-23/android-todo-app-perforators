package com.example.todolist.di.todoitems

import com.example.todolist.presentation.todoitems.TodoListFragment
import dagger.Component

@TodoItemsScreenScope
@Component
interface TodoItemsScreenComponent {

    fun inject(fragment: TodoListFragment)


}