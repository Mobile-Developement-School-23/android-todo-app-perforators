package com.example.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings_api.SettingsRepository
import com.example.settings_api.models.Theme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val settings = repository.fetchSettings()

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    fun updateTheme(newTheme: Theme) {
        viewModelScope.launch {
            repository.updateTheme(newTheme)
        }
        goBack()
    }

    fun goBack() {
        viewModelScope.launch {
            _events.send(Event.GoBack)
        }
    }

    sealed interface Event {
        object GoBack : Event
    }
}