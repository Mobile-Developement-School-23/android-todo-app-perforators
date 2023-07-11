package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.TodoItemsRepository
import com.example.todolist.di.TodoItemsScreenScope
import com.example.todolist.navigation.TodoListNavCommandProvider
import javax.inject.Inject

@TodoItemsScreenScope
class TodoListViewModelFactory @Inject constructor(
    private val repository: TodoItemsRepository,
    private val commandProvider: TodoListNavCommandProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == TodoListViewModel::class.java)
        return TodoListViewModel(repository, commandProvider) as T
    }
}