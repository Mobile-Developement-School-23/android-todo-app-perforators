package com.example.di

import com.example.todo_api.TodoItemsRepository
import com.example.todolist.navigation.TodoNavCommandProvider
import kotlin.properties.Delegates

interface TodoFeatureDependencies {

    fun repository(): TodoItemsRepository

    fun todoItemsNavCommandProvider(): TodoNavCommandProvider
}

interface TodoFeatureDepsProvider {
    val deps: TodoFeatureDependencies

    companion object : TodoFeatureDepsProvider by TodoFeatureDepsStore
}

object TodoFeatureDepsStore : TodoFeatureDepsProvider {

    override var deps: TodoFeatureDependencies by Delegates.notNull()
}