package com.example.todolist.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.TokenRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val repository: TokenRepository
) : ViewModel() {

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    fun addDefaultToken() {
        viewModelScope.launch {
            repository.addDefaultToken()
            _events.send(Event.ShowAllTodoItems)
        }
    }

    fun addYandexToken(token: String) {
        viewModelScope.launch {
            repository.addYandexToken(token)
            _events.send(Event.ShowAllTodoItems)
        }
    }

    sealed interface Event {

        object ShowAllTodoItems : Event
    }
}