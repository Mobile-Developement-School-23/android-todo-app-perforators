package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_api.TodoItemsRepository
import com.example.navigation.NavCommand
import com.example.todo_api.models.Items
import com.example.todo_api.models.TodoItem
import com.example.todolist.navigation.TodoListNavCommandProvider
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
import java.util.Date

class TodoListViewModel(
    private val repository: TodoItemsRepository,
    private val commandProvider: TodoListNavCommandProvider
) : ViewModel() {

    private val showAll = MutableStateFlow(false)
    private val items: Flow<Items>
        get() = repository.observeAll()

    val state = items.combine(showAll) { items, showAll ->
        val (lastItems, areActual) = items
        val newItems = if (showAll) lastItems else lastItems.filter { it.isDone.not() }
        val countCompleted = lastItems.count { it.isDone }
        ScreenState(newItems, areActual, countCompleted, showAll)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ScreenState())

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadAll()
    }

    fun loadAll() = viewModelScope.launch {
        repository.fetchAll()
            .onFailure { _events.send(Event.ShowError(it.message.toString())) }
        _events.send(Event.HideRefreshProgressBar)
    }

    fun toggleDone(item: TodoItem) = viewModelScope.launch {
        val currentDate = Date(System.currentTimeMillis())
        repository.edit(
            item.copy(isDone = item.isDone.not(), changeData = currentDate)
        ).onFailure { _events.send(Event.ShowError(it.message.toString())) }
    }

    fun removeItemBy(index: Int) = viewModelScope.launch {
        repository.remove(state.value.items[index])
            .onFailure { _events.send(Event.ShowError(it.message.toString())) }
    }

    fun toggleShowingItems() {
        showAll.update {
            it.not()
        }
    }

    fun createNewTodoItem() {
        viewModelScope.launch {
            _events.send(Event.CreateNewTodoItem(commandProvider.navigateToEditScreen()))
        }
    }

    fun edit(item: TodoItem) {
        viewModelScope.launch {
            _events.send(Event.EditTodoItem(item.id, commandProvider.navigateToEditScreen()))
        }
    }

    data class ScreenState(
        val items: List<TodoItem> = emptyList(),
        val areActual: Boolean = false,
        val countCompleted: Int = 0,
        val areShownAllElements: Boolean = false
    )

    sealed interface Event {
        data class CreateNewTodoItem(val command: NavCommand) : Event
        object HideRefreshProgressBar : Event
        data class ShowError(val text: String) : Event
        data class EditTodoItem(val itemId: String, val command: NavCommand) : Event
    }
}