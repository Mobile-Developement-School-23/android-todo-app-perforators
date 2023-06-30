package com.example.todolist.di.detail

import com.example.todolist.domain.TodoItemsRepository

interface DetailScreenDependencies {

    fun repository(): TodoItemsRepository
}