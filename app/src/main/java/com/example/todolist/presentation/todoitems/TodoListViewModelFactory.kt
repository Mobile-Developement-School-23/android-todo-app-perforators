package com.example.todolist.presentation.todoitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.di.todoitems.TodoItemsScreenScope
import com.example.todolist.domain.TodoItemsRepository
import javax.inject.Inject

@TodoItemsScreenScope
class TodoListViewModelFactory @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoListViewModel(repository) as T
    }
}