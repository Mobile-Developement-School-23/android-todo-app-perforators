package com.example.todolist.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.domain.TokenRepository
import javax.inject.Inject

class AuthorizationViewModelFactory @Inject constructor(
    private val repository: TokenRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthorizationViewModel(repository) as T
    }
}