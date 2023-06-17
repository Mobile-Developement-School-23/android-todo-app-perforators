package com.example.todolist.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.models.Importance
import com.example.todolist.models.TodoItem
import com.example.todolist.storage.TodoItemsRepository
import com.example.todolist.storage.TodoItemsRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date

class DetailViewModel(
    private val itemId: String,
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl
) : ViewModel() {

    private val _item = MutableStateFlow(TodoItem.empty())
    val item = _item.asStateFlow()

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        if (itemId.isNotEmpty()) loadItem()
    }

    private fun loadItem() {
        viewModelScope.launch {
            repository.fetchOne(itemId)
                .onSuccess { _item.value = it }
                .onFailure { _events.send(Event.GoBack) }
        }
    }

    fun delete() {
        repository.remove(_item.value)
        cancel()
    }

    fun save(text: String, importance: Importance, deadline: Date?) {
        val currentItem = _item.value
        val currentDate = Date(System.currentTimeMillis())
        val newItem = currentItem.copy(
            text = text,
            importance = importance,
            deadline = deadline,
            creationDate = currentDate,
            changeData = if (itemId.isNotEmpty()) currentDate else null
        )
        repository.save(newItem)
        cancel()
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
        object GoBack : Event
    }
}