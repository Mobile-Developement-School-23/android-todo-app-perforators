package com.example.todolist.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.di.detail.DetailScreenScope
import com.example.todolist.domain.TodoItemsRepository
import javax.inject.Inject

@DetailScreenScope
class DetailViewModelFactory @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}