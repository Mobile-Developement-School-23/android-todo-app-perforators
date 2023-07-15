package com.example.edittodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ImportancePickViewModel : ViewModel() {

    private val _pickedImportanceId = Channel<Int>(BUFFERED)
    val pickedImportanceId = _pickedImportanceId.receiveAsFlow()

    fun pickImportance(id: Int) {
        viewModelScope.launch {
            _pickedImportanceId.send(id)
        }
    }
}