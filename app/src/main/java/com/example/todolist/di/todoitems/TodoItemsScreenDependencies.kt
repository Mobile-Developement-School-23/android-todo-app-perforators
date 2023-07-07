package com.example.todolist.di.todoitems

import com.example.todolist.domain.TodoItemsRepository

interface TodoItemsScreenDependencies {

    fun repository(): TodoItemsRepository
}