package com.example.todolist.data.remote.mappers

import com.example.todolist.utils.convertToDate
import com.example.todolist.data.remote.api.dto.TodoDto
import com.example.todolist.domain.models.TodoItem
import javax.inject.Inject

class TodoMapper @Inject constructor(
    private val importanceMapper: ImportanceMapper
) {

    fun map(todoDtoList: List<TodoDto>): List<TodoItem> {
        return todoDtoList.map(::map)
    }

    fun map(todoItemList: List<TodoItem>): List<TodoDto> {
        return todoItemList.map(::map)
    }

    fun map(todoDto: TodoDto) = TodoItem(
        id = todoDto.id,
        text = todoDto.text,
        importance = importanceMapper.map(todoDto.importance),
        isDone = todoDto.isDone,
        creationDate = todoDto.createdAt.convertToDate(),
        changeData = todoDto.changedAt.convertToDate(),
        deadline = todoDto.deadline?.convertToDate(),
        lastUpdatedBy = todoDto.lastUpdatedBy
    )

    fun map(todoItem: TodoItem) = TodoDto(
        id = todoItem.id,
        text = todoItem.text,
        importance = importanceMapper.map(todoItem.importance),
        deadline = todoItem.deadline?.time,
        isDone = todoItem.isDone,
        color = null,
        createdAt = todoItem.creationDate.time,
        changedAt = todoItem.changeData.time,
        lastUpdatedBy = todoItem.lastUpdatedBy
    )
}