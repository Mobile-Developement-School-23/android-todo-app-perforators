package com.example.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.domain.TokenRepository
import com.example.navigation.NavCommand
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val repository: TokenRepository,
    private val navigation: AuthorizationNavCommandProvider
) : ViewModel() {

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    fun addDefaultToken() {
        viewModelScope.launch {
            repository.addDefaultToken()
            _events.send(Event.ShowAllTodoItems(navigation.navigateToTodoList()))
        }
    }

    fun addYandexToken(token: String) {
        viewModelScope.launch {
            repository.addYandexToken(token)
            _events.send(Event.ShowAllTodoItems(navigation.navigateToTodoList()))
        }
    }

    sealed interface Event {

        data class ShowAllTodoItems(val command: NavCommand) : Event
    }
}