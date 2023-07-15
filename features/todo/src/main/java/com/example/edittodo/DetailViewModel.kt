package com.example.edittodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commom.convertToDate
import com.example.commom.splitOnTimeAndDate
import com.example.todo_api.TodoItemsRepository
import com.example.todo_api.models.Importance
import com.example.todo_api.models.TodoItem
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

    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    private val _events = Channel<Event>(BUFFERED)
    val events = _events.receiveAsFlow()

    private var itemId: String = ""
    private var selectedParams = SelectedParams()

    fun loadItem(itemId: String) {
        this.itemId = itemId
        if (itemId.isEmpty()) return
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.fetchOne(itemId)
                .onSuccess { item ->
                    selectedParams = SelectedParams.from(item)
                    _state.update { it.copy(item = item, isLoading = false, isNew = false) }
                }
                .onFailure {
                    _events.send(Event.ShowError(it.message.toString()))
                    _events.send(Event.GoBack)
                }
        }
    }

    fun select(action: SelectAction) {
        selectedParams.apply(action)
    }

    fun delete() {
        viewModelScope.launch {
            repository.remove(_state.value.item)
                .onFailure { _events.send(Event.ShowError(it.message.toString())) }
            cancel()
        }
    }

    fun save() {
        val currentItem = _state.value.item
        val currentDate = Date(System.currentTimeMillis())
        val newItem = currentItem.copy(
            text = selectedParams.text,
            importance = selectedParams.importance,
            deadline = calculateDeadline(),
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

    private fun calculateDeadline(): Date? {
        if (selectedParams.dateDeadline == null) return null
        return convertToDate(selectedParams.dateDeadline!!, selectedParams.timeDeadline)
    }

    private fun cancel() {
        viewModelScope.launch {
            _events.send(Event.GoBack)
        }
    }

    private data class SelectedParams(
        var text: String = "",
        var importance: Importance = Importance.NORMAL,
        var dateDeadline: String? = null,
        var timeDeadline: String? = null
    ) {

        fun apply(action: SelectAction) = when (action) {
            is SelectAction.SelectImportance -> importance = action.importance
            is SelectAction.SelectText -> text = action.text
            is SelectAction.SelectDateDeadline -> dateDeadline = action.date
            is SelectAction.SelectTimeDeadline -> timeDeadline = action.time
        }

        companion object {
            fun from(item: TodoItem): SelectedParams {
                val (date, time) = item.deadline?.splitOnTimeAndDate() ?: (null to null)
                return SelectedParams(
                    text = item.text,
                    importance = item.importance,
                    dateDeadline = date,
                    timeDeadline = time
                )
            }
        }
    }

    sealed interface SelectAction {
        data class SelectImportance(val importance: Importance) : SelectAction
        data class SelectText(val text: String) : SelectAction
        data class SelectDateDeadline(val date: String) : SelectAction
        data class SelectTimeDeadline(val time: String) : SelectAction
    }

    data class ScreenState(
        val item: TodoItem = TodoItem.createEmpty(),
        val isNew: Boolean = true,
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data class ShowError(val text: String) : Event
        object GoBack : Event
    }
}