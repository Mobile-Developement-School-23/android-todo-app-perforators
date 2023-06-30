package com.example.todolist.data.local.mappers

import com.example.todolist.utils.convertToDate
import com.example.todolist.data.local.database.SyncStatus
import com.example.todolist.data.local.database.TodoEntity
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoMapper {

    fun map(entities: Flow<List<TodoEntity>>) = entities.map { map(it) }

    fun map(items: List<TodoItem>) = items.map { map(it) }

    fun map(entity: TodoEntity) = TodoItem(
        id = entity.id,
        text = entity.text,
        importance = entity.importance,
        isDone = entity.isDone,
        creationDate = entity.creationDate.convertToDate(),
        changeData = entity.changeData.convertToDate(),
        deadline = entity.deadline?.convertToDate(),
        lastUpdatedBy = entity.lastUpdatedBy
    )

    fun map(entities: List<TodoEntity>) = entities.map { map(it) }

    fun map(item: TodoItem) = TodoEntity(
        id = item.id,
        text = item.text,
        importance = item.importance,
        isDone = item.isDone,
        creationDate = item.creationDate.time,
        changeData = item.changeData.time,
        deadline = item.deadline?.time,
        lastUpdatedBy = item.lastUpdatedBy,
        syncStatus = SyncStatus.SYNCHRONIZED
    )
}