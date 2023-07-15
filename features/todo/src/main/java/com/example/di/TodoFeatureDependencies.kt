package com.example.di

import com.example.settings_api.SettingsRepository
import com.example.todo_api.TodoItemsRepository
import com.example.navigation.TodoNavCommandProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.properties.Delegates

interface TodoFeatureDependencies {

    fun todoRepository(): TodoItemsRepository

    fun dispatcher(): CoroutineDispatcher

    fun settingsRepository(): SettingsRepository

    fun todoItemsNavCommandProvider(): TodoNavCommandProvider
}

interface TodoFeatureDepsProvider {
    val deps: TodoFeatureDependencies

    companion object : TodoFeatureDepsProvider by TodoFeatureDepsStore
}

object TodoFeatureDepsStore : TodoFeatureDepsProvider {

    override var deps: TodoFeatureDependencies by Delegates.notNull()
}