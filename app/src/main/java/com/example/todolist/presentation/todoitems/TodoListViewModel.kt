package com.example.todolist.presentation.todoitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.models.TodoItem
import com.example.todolist.domain.TodoItemsRepository
import com.example.todolist.data.TodoItemsRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl
) : ViewModel() {

    private val showAll = MutableStateFlow(false)
    private val items: Flow<List<TodoItem>>
        get() = repository.fetchAll()

    val state = items.combine(showAll) { lastItems, showAll ->
        val newItems = if (showAll) lastItems else lastItems.filter { it.isDone.not() }
        val countCompleted = lastItems.count { it.isDone }
        ScreenState(newItems, countCompleted, showAll)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState())

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    fun toggleDone(item: TodoItem) {
        repository.save(item.copy(isDone = item.isDone.not()))
    }

    fun removeItemBy(index: Int) {
        repository.remove(state.value.items[index])
    }

    fun toggleShowingItems() {
        showAll.update {
            it.not()
        }
    }

    fun createNewTodoItem() {
        viewModelScope.launch {
            _events.send(Event.CreateNewTodoItem)
        }
    }

    fun edit(item: TodoItem) {
        viewModelScope.launch {
            _events.send(Event.EditTodoItem(item.id))
        }
    }

    data class ScreenState(
        val items: List<TodoItem> = emptyList(),
        val countCompleted: Int = 0,
        val areShownAllElements: Boolean = false
    )

    sealed interface Event {
        object CreateNewTodoItem : Event
        data class EditTodoItem(val itemId: String) : Event
    }
}