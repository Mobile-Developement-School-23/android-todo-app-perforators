package com.example.edittodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_api.TodoItemsRepository
import com.example.todo_api.models.TodoItem
import com.example.todo_api.models.importanceFrom
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel(
    private val repository: TodoItemsRepository
) : ViewModel() {

    private val _item = MutableStateFlow(TodoItem.createEmpty())
    val item = _item.asStateFlow()

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    private var itemId: String = ""

    fun loadItem(itemId: String) {
        this.itemId = itemId
        if (itemId.isEmpty()) return
        viewModelScope.launch {
            repository.fetchOne(itemId)
                .onSuccess { _item.value = it }
                .onFailure { _events.send(Event.ShowError(it.message.toString())) }
        }
    }

    fun changeImportance(importanceId: Int) {
        val importance = importanceFrom(importanceId)
        _item.update { it.copy(importance = importance) }
    }

    fun delete() {
        viewModelScope.launch {
            repository.remove(_item.value)
                .onFailure { _events.send(Event.ShowError(it.message.toString())) }
            cancel()
        }
    }

    fun save(text: String, deadline: Date?) {
        val currentItem = _item.value
        val currentDate = Date(System.currentTimeMillis())
        val newItem = currentItem.copy(
            text = text,
            deadline = deadline,
            changeData = currentDate
        )
        viewModelScope.launch {
            if (itemId.isEmpty()) {
                repository.addNew(newItem)
            } else {
                repository.edit(newItem)
            }.onFailure { _events.send(Event.ShowError(it.message.toString())) }
            cancel()
        }
    }

    fun pickImportance() {
        viewModelScope.launch {
            _events.send(Event.PickImportance)
        }
    }

    fun showDatePicker() {
        viewModelScope.launch {
            _events.send(Event.ShowDatePicker)
        }
    }

    fun cancel() {
        viewModelScope.launch {
            _events.send(Event.GoBack)
        }
    }

    sealed interface Event {
        object ShowDatePicker : Event
        data class ShowError(val text: String) : Event
        object PickImportance : Event
        object GoBack : Event
    }
}