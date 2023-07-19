package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.di.TodoFeatureScope
import com.example.settings_api.SettingsRepository
import com.example.todo_api.TodoItemsRepository
import com.example.navigation.TodoNavCommandProvider
import javax.inject.Inject

@TodoFeatureScope
class TodoListViewModelFactory @Inject constructor(
    private val repository: TodoItemsRepository,
    private val settingsRepository: SettingsRepository,
    private val commandProvider: TodoNavCommandProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == TodoListViewModel::class.java)
        return TodoListViewModel(repository, settingsRepository, commandProvider) as T
    }
}