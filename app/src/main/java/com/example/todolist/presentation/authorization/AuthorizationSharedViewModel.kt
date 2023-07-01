package com.example.todolist.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthorizationSharedViewModel : ViewModel() {

    private val _authorizationEvent = Channel<Unit>(Channel.BUFFERED)
    val authorizationEvent = _authorizationEvent.receiveAsFlow()

    private val _authorizationResult = Channel<AuthorizationResult>(Channel.BUFFERED)
    val authorizationResult = _authorizationResult.receiveAsFlow()

    fun authorize() {
        viewModelScope.launch {
            _authorizationEvent.send(Unit)
        }
    }

    fun sendToken(result: AuthorizationResult) {
        viewModelScope.launch {
            _authorizationResult.send(result)
        }
    }

    sealed interface AuthorizationResult {
        data class Success(val token: String) : AuthorizationResult
        object Failure : AuthorizationResult
    }
}