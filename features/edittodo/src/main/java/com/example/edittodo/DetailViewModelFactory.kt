package com.example.edittodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.edittodo.di.DetailScreenScope
import com.example.todo_api.TodoItemsRepository
import javax.inject.Inject

@DetailScreenScope
class DetailViewModelFactory @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == DetailViewModel::class.java)
        return DetailViewModel(repository) as T
    }
}