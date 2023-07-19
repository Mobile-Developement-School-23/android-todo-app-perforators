package com.example.todo.remote.mappers

import com.example.commom.convertToDate
import com.example.todo.remote.api.dto.TodoDto
import com.example.todo_api.models.TodoItem
import javax.inject.Inject

class RemoteTodoMapper @Inject constructor(
    private val importanceMapper: ImportanceMapper
) {

    fun mapTodoList(todoDtoList: List<TodoDto>): List<TodoItem> {
        return todoDtoList.map(::map)
    }

    fun mapItemList(todoItemList: List<TodoItem>): List<TodoDto> {
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