package com.example.todolist.di

import com.example.todo_api.TodoItemsRepository
import com.example.todolist.navigation.TodoListNavCommandProvider
import kotlin.properties.Delegates.notNull

interface TodoItemsScreenDependencies {

    fun repository(): TodoItemsRepository

    fun todoItemsNavCommandProvider(): TodoListNavCommandProvider
}

interface TodoItemsDepsProvider {
    val deps: TodoItemsScreenDependencies

    companion object : TodoItemsDepsProvider by TodoItemsDepsStore
}

object TodoItemsDepsStore : TodoItemsDepsProvider {

    override var deps: TodoItemsScreenDependencies by notNull()
}